package com.example.eloqualogger.controller;

import com.example.eloqualogger.model.ConfigureEntity;
import com.example.eloqualogger.service.ConfigureEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/configure_entities")
public class ConfigureEntityController {

    @Autowired
    private ConfigureEntityService service;

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public ConfigureEntity createConfigureEntity(@RequestParam Map<String, String> formData, @RequestParam Map<String, Object> queryStrings){

        Map<String, Object> newConfigureEntity = new HashMap<>();
        newConfigureEntity.put("formData", formData);
        newConfigureEntity.put("queryStrings",  queryStrings);
        return service.createConfigureEntity(newConfigureEntity);
    }

    @GetMapping()
    @ResponseBody
    public String getForm() {
        return service.getConfigForm();
    }

    @GetMapping("/get_all")
    public List<ConfigureEntity> getAllConfigureEntities(){
        return service.getAllConfireEntities();
    }
}
