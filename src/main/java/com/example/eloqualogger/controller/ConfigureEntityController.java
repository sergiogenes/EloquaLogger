package com.example.eloqualogger.controller;

import com.example.eloqualogger.model.ConfigureEntity;
import com.example.eloqualogger.service.ConfigureEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/configure_entities")
public class ConfigureEntityController {

    @Autowired
    private ConfigureEntityService service;

    @PostMapping()
    public ConfigureEntity createConfigureEntity(
            @RequestParam Map<String, String> queryStrings,
            @RequestBody Map<String, Object> body) {

        Map<String, Object> newConfigureEntity = new HashMap<>();
        newConfigureEntity.put("body", body);
        newConfigureEntity.put("queryStrings",  queryStrings);


        // Extrae el id_instance del query string
        String idInstance = queryStrings.get("id_instance");

        // Construye el cuerpo de la solicitud PUT
        Map<String, Object> putBody = new HashMap<>();
        putBody.put("recordDefinition", Map.of(
                "ContactID", "{{Contact.Id}}",
                "EmailAddress", "{{Contact.Field(C_EmailAddress)}}"
        ));
        putBody.put("height", 256);
        putBody.put("width", 256);
        putBody.put("editorImageUrl", "https://img04.en25.com/EloquaImages/clients/IngramMicroLATAM/%7B5f315c7b-a380-47af-95aa-140b9a43bd21%7D_32x32.png");
        putBody.put("requiresConfiguration", false);

        // Configura los headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crea la entidad HTTP
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(putBody, headers);

        // Define la URL de destino
        String url = "https://secure.eloqua.com/api/cloud/1.0/contents/instances/" + idInstance;

        // Realiza la solicitud PUT
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        // Devuelve la respuesta de la solicitud PUT
        return service.createConfigureEntity(newConfigureEntity);
    }

    @GetMapping()
    @ResponseBody
    public String getForm(@RequestParam Map<String, Object> queryStrings) {
        return service.getConfigForm(queryStrings);
    }

    @GetMapping("/get_all")
    public List<ConfigureEntity> getAllConfigureEntities(){
        return service.getAllConfireEntities();
    }
}
