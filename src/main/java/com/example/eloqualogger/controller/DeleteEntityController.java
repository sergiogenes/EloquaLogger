package com.example.eloqualogger.controller;

import com.example.eloqualogger.model.DeleteEntity;
import com.example.eloqualogger.service.DeleteEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/delete_entities")
public class DeleteEntityController {

    @Autowired
    private DeleteEntityService service;

    @PostMapping
    public DeleteEntity createDeleteEntity(@RequestParam Map<String, Object> queryStrings, @RequestBody Map<String, Object> body){

        Map<String, Object> newDeleteEntity = new HashMap<>();

        newDeleteEntity.put("queryStrings", queryStrings);
        newDeleteEntity.put("body", body);
        return service.createDeleteEntity(newDeleteEntity);
    }

    @DeleteMapping
    public DeleteEntity deleteDeleteEntity(@RequestParam Map<String, Object> queryStrings, @RequestBody Map<String, Object> body){

        Map<String, Object> newDeleteEntity = new HashMap<>();

        newDeleteEntity.put("queryStrings", queryStrings);
        newDeleteEntity.put("body", body);
        return service.createDeleteEntity(newDeleteEntity);
    }

    @GetMapping
    public List<DeleteEntity> getAllDeleteEntities (){
        return service.getAllDeleteEntities();
    }
}
