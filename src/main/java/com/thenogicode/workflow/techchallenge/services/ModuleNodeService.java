package com.thenogicode.workflow.techchallenge.services;

import com.thenogicode.workflow.techchallenge.dto.ModuleNodeDto;
import com.thenogicode.workflow.techchallenge.entities.ModuleNode;
import com.thenogicode.workflow.techchallenge.exception.WorkflowException;
import com.thenogicode.workflow.techchallenge.repositories.ModuleNodeRepository;
import com.thenogicode.workflow.techchallenge.utils.ListUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModuleNodeService {

    private final ModuleNodeRepository repository;

    public ModuleNodeDto create(@NonNull ModuleNodeDto dto) {
        log.debug("Creating Module Node");

        final var mapToEntity = mapToEntity(dto, null);

        final var module = repository.save(mapToEntity);

        return mapToResource(module);
    }

    public ModuleNodeDto retrieveModuleByKey(@NonNull String key) throws WorkflowException {

        log.debug("Retrieve Module Node by Key {}", key);

        final var module = repository.findModuleNodeByModuleKey(key).orElseThrow(() -> WorkflowException.notFound("No module found with key indicated", "NotFoundException"));

        return mapToResource(module);
    }

    public List<String> getModuleDependencies(@NonNull String key) throws WorkflowException {
        final var module = retrieveModuleByKey(key);

        try {
            final var setTree= flatTree(module.getChildren());

            if(ListUtils.isNullOrEmpty(setTree)){
                throw WorkflowException.notFound("No dependencies found for key " + key, "NotFoundException");
            }

            return setTree.stream().collect(Collectors.toList());
        } catch (RuntimeException re){
            throw WorkflowException.internalServerError(re.getMessage(), re.getLocalizedMessage());
        }

    }

    public static Set<String> flatTree(List<ModuleNodeDto> toFlat) {
        if(ListUtils.isNullOrEmpty(toFlat)) return null;

        return flatTree(toFlat, LinkedHashSet::new);
    }

    public static Set<String> flatTree(List<ModuleNodeDto> toFlat, Supplier<LinkedHashSet<String>> setSupplier){
        return toFlat.stream().flatMap(child -> {
                    try {
                        return child.streamChildren();
                    } catch (WorkflowException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }).map(node -> node.getModuleKey())
                .collect(Collectors.toCollection(setSupplier));
    }

    private ModuleNode mapToEntity(@NonNull ModuleNodeDto dto, ModuleNode parent){

        final var current = ModuleNode.builder()
                .id(dto.getId())
                .moduleKey(dto.getModuleKey())
                .parent(parent)
                .build();

        final var children = !ListUtils.isNullOrEmpty(dto.getChildren())? dto.getChildren().stream()
                .map(c -> this.mapToEntity(c, current)).collect(Collectors.toList()) : null;

        current.setChildren(children);

        return current;
    }

    private ModuleNodeDto mapToResource(@NonNull ModuleNode entity){

        final var children = !ListUtils.isNullOrEmpty(entity.getChildren())? entity.getChildren().stream().map(this::mapToResource).collect(Collectors.toList()) : null;

        return ModuleNodeDto.builder()
                .id(entity.getId())
                .moduleKey(entity.getModuleKey())
                .children(children)
                .build();

    }

}
