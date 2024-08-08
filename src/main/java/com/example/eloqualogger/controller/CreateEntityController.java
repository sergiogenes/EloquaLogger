package com.example.eloqualogger.controller;

import com.example.eloqualogger.model.CreateEntity;
import com.example.eloqualogger.service.CreateEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/create_entities")
public class CreateEntityController {

    @Autowired
    private CreateEntityService service;

    @PostMapping
    public CreateEntity createNewCreateEntity(@RequestParam Map<String, Object> queryStrings, @RequestBody Map<String, Object> body){

        Map<String, Object> newCreateEntity = new HashMap<>();

        newCreateEntity.put("queryStrings", queryStrings);
        newCreateEntity.put("body", body);
        return service.createCreateEntity(newCreateEntity);

    }

    @GetMapping
    public List<CreateEntity> getAllCreateEntities (){
        return service.getAllCreateEntities();
    }
}
