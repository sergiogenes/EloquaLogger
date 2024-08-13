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
        version.put("version", "0.12 - Se agrega cabecera Authorization y token al pedido Put hacia Eloqua" );
        return  version;
    }

}
