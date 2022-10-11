package com.thenogicode.workflow.techchallenge.controllers;

import com.thenogicode.workflow.techchallenge.dto.ModuleNodeDto;
import com.thenogicode.workflow.techchallenge.exception.WorkflowException;
import com.thenogicode.workflow.techchallenge.services.ModuleNodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/module-node")
@RequiredArgsConstructor
public class ModuleNodeController {

    private final ModuleNodeService service;

    @PostMapping()
    public ModuleNodeDto createModule(@RequestBody ModuleNodeDto dto) {
        return service.create(dto);
    }

    @PostMapping("/retrieve/{key}")
    public ModuleNodeDto retrieveModuleByKey(@PathVariable final String key) throws WorkflowException {
        return service.retrieveModuleByKey(key);
    }

    @GetMapping("/dependencies/{key}")
    public List<String> getModuleDependencies(@PathVariable final String key) throws WorkflowException {
        return service.getModuleDependencies(key);
    }

}
