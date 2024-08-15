package com.example.eloqualogger.controller;

import com.example.eloqualogger.model.ConfigureEntity;
import com.example.eloqualogger.service.ConfigureEntityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/configure_entities")
public class ConfigureEntityController {

    @Value("${eloqua.auth.token}")
    private String eloquaToken;


    @Autowired
    private ConfigureEntityService service;

    @PostMapping()
    public ResponseEntity<?> createConfigureEntity(
            @RequestParam Map<String, String> queryStrings,
            @RequestBody Map<String, Object> body) {

        Map<String, Object> newConfigureEntity = new HashMap<>();
        newConfigureEntity.put("body", body);
        newConfigureEntity.put("queryStrings", queryStrings);

        String instanceId = queryStrings.get("instance_id");

        // Se construye el cuerpo de la solicitud PUT
        Map<String, Object> putBody = new HashMap<>();

        Map<String, String> recordDefinition = new HashMap<>();
        recordDefinition.put("ContactID", "{{Contact.Id}}");
        recordDefinition.put("EmailAddress", "{{Contact.Field(C_EmailAddress)}}");

        putBody.put("recordDefinition", recordDefinition);
        putBody.put("height", 256);
        putBody.put("width", 256);
        putBody.put("editorImageUrl", "https://img04.en25.com/EloquaImages/clients/IngramMicroLATAM/%7B5f315c7b-a380-47af-95aa-140b9a43bd21%7D_32x32.png");
        putBody.put("requiresConfiguration", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + eloquaToken);
        try {
        String jsonBody = new ObjectMapper().writeValueAsString(putBody);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        String url = "https://secure.p04.eloqua.com/api/cloud/1.0/contents/instances/" + instanceId;


            System.out.println("URL: " + url);
            System.out.println("Serialized JSON Body: " + jsonBody);
            System.out.println("Authorization Header: " + headers.get("Authorization"));

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
            System.out.println("Response: " + response.getBody());

            return ResponseEntity.ok(service.createConfigureEntity(newConfigureEntity));

        } catch (JsonProcessingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la serialización del JSON: " + e.getMessage());

        }catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Error en la solicitud PUT: " + e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado: " + e.getMessage());
        }
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
