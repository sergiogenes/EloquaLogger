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
    public Map<String, Object> createNewCreateEntity(@RequestParam Map<String, Object> queryStrings, @RequestBody Map<String, Object> body){

        Map<String, Object> newCreateEntity = new HashMap<>();

        newCreateEntity.put("queryStrings", queryStrings);
        newCreateEntity.put("body", body);
        service.createCreateEntity(newCreateEntity);

        // Estructura JSON de la respuesta
        Map<String, Object> response = new HashMap<>();
        Map<String, String> recordDefinition = new HashMap<>();
        recordDefinition.put("ContactID", "{{Contact.Id}}");
        //recordDefinition.put("EmailAddress", "{{Contact.Field(C_EmailAddress)}}");

        response.put("recordDefinition", recordDefinition);
        response.put("height", 256);
        response.put("width", 256);
        response.put("editorImageUrl", "https://img04.en25.com/EloquaImages/clients/IngramMicroLATAM/%7B5f315c7b-a380-47af-95aa-140b9a43bd21%7D_32x32.png");
        response.put("requiresConfiguration", true);

        return response;

    }

    @GetMapping
    public List<CreateEntity> getAllCreateEntities (){
        return service.getAllCreateEntities();
    }
}
