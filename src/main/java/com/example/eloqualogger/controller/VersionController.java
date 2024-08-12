package com.example.eloqualogger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/version")
public class VersionController {

    @GetMapping
    public Map<String, Object> getVersion(){
        Map<String, Object> version = new HashMap<>();
        version.put("version", "0.6 - Se agregan queryStrings a pedido post del formulario de configuración");
        return  version;
    }

}
