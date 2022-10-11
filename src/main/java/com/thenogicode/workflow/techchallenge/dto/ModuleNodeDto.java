package com.thenogicode.workflow.techchallenge.dto;

import com.thenogicode.workflow.techchallenge.exception.WorkflowException;
import com.thenogicode.workflow.techchallenge.utils.ListUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleNodeDto {

    private Long id;

    private String moduleKey;

    private List<ModuleNodeDto> children;

    public Stream<ModuleNodeDto> streamChildren() throws WorkflowException {
        if (!ListUtils.isNullOrEmpty(children)) {

            List<ModuleNodeDto> flatChildren= children.stream().flatMap(child -> {
                try {
                    return child.streamChildren();
                } catch (WorkflowException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());

            if(flatChildren.stream().anyMatch(c -> c.getModuleKey().equals(moduleKey))){
                throw new WorkflowException("Cyclic dependency", "CyclicDependencyException");
            }

            return Stream.concat(children.stream().flatMap(child -> {
                try {
                    return child.streamChildren();
                } catch (WorkflowException e) {
                    throw new RuntimeException(e);
                }
            }), Stream.of(this));

        }
        else {
            return Stream.of(this);
        }
    }

}
