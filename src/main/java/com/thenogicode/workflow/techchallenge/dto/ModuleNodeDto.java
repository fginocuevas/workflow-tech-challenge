package com.thenogicode.workflow.techchallenge.dto;

import com.thenogicode.workflow.techchallenge.utils.ListUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleNodeDto {

    private Long id;

    private String moduleKey;

    private List<ModuleNodeDto> children;

    public Stream<ModuleNodeDto> streamChildren() {
        if (!ListUtils.isNullOrEmpty(children))
            return Stream.concat(children.stream().flatMap(ModuleNodeDto::streamChildren), Stream.of(this));
        else
            return Stream.of(this);
    }

}
