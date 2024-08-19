package com.example.eloqualogger.service;

import com.example.eloqualogger.model.NotificationEntity;
import com.example.eloqualogger.repository.NotificationEntityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationEntityService {
    @Value("${eloqua.auth.token}")
    private String eloquaToken;

    @Autowired
    private NotificationEntityRepository repository;

    public NotificationEntity createNotificationEntity(Map<String, Object> data){
        NotificationEntity newNotificationEntity = new NotificationEntity(data);
        return repository.save(newNotificationEntity);
    }

    public List<NotificationEntity> getAllNotificationEntities(){
        return repository.findAll();
    }

    @Async
    public void processEloquaRequest(Map<String, Object> notificationEntity) {
        String instanceId = ((String) ((Map<String, Object>) notificationEntity.get("queryStrings")).get("instance_id")).replace("-", "");
        String executionId = (String) ((Map<String, Object>) notificationEntity.get("queryStrings")).get("execution_id");

        String postBody = String.format(
                """
                {
                    "name": "AwesomeApp Content Response Bulk Import",
                    "updateRule": "always",
                    "fields": {
                        "EmailAddress": "{{Contact.Field(C_EmailAddress)}}",
                        "Content": "{{ContentInstance(%s)}}"
                    },
                    "identifierFieldName": "EmailAddress"
                }
                """, instanceId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + eloquaToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(postBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://secure.p04.eloqua.com/api/bulk/2.0/contacts/imports";

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            System.out.println("Response: " + response.getBody());

            // Obtener el URI del cuerpo de la respuesta
            String responseBody = response.getBody();
            String importUri = extractUriFromResponse(responseBody);

            // Luego, realizar el siguiente POST a la URL obtenida
            String postDataBody = createPostDataBody(notificationEntity);
            postToEloqua(importUri, postDataBody);

            // Luego, realizar el tercer post de Sincronizacion
            syncImport(importUri);

        } catch (HttpClientErrorException e) {
            System.err.println("Error en la solicitud POST: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    private String extractUriFromResponse(String responseBody) {
        // Extrae el valor de "uri" del JSON de respuesta
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("uri").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al procesar la respuesta de Eloqua", e);
        }
    }

    private String createPostDataBody(Map<String, Object> notificationEntity) {
        List<Map<String, Object>> items = (List<Map<String, Object>>) ((Map<String, Object>) notificationEntity.get("body")).get("items");
        StringBuilder dataBuilder = new StringBuilder("[");

        for (Map<String, Object> item : items) {
            String contactId = (String) item.get("ContactID");

            // Reemplazar :parentId con 186 y contactId con el valor del campo contactId
            String url = String.format("https://secure.p04.eloqua.com/api/rest/2.0/data/customObject/186/instances?search=contactId='%s'", contactId);

            // Realizar la solicitud GET
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + eloquaToken);

            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            // Procesar la respuesta para obtener los valores necesarios
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode;
            try {
                rootNode = objectMapper.readTree(responseBody);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error al procesar la respuesta de Eloqua", e);
            }

            JsonNode elements = rootNode.get("elements");
            if (elements != null && elements.isArray()) {
                StringBuilder contentBuilder = new StringBuilder();

                for (JsonNode element : elements) {
                    String email = element.get("fieldValues").get(0).get("value").asText();
                    String contrato = element.get("fieldValues").get(1).get("value").asText();
                    String link = element.get("fieldValues").get(2).get("value").asText();

                    contentBuilder.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>", contrato, email, link));
                }

                // Formar el objeto JSON con el email y el contenido generado
                String emailAddress = (String) item.get("EmailAddress");
                dataBuilder.append(String.format(
                        """
                        {
                            "EmailAddress": "%s",
                            "Content": "%s"
                        },
                        """, emailAddress, contentBuilder.toString()));
            }
        }

        // Quitar la última coma y cerrar el array JSON
        dataBuilder.setLength(dataBuilder.length() - 1);
        dataBuilder.append("]");

        return dataBuilder.toString();
    }


    private void postToEloqua(String importUri, String postDataBody) {
        String url = "https://secure.p04.eloqua.com/api/bulk/2.0" + importUri + "/data";
        System.out.println("url: " + url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + eloquaToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(postDataBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            System.out.println("Data Post Response: " + response.getBody());
        } catch (HttpClientErrorException e) {
            System.err.println("Error en la solicitud POST de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado al enviar los datos: " + e.getMessage());
        }
    }

    private void syncImport(String importUri) {
        String syncBody = String.format(
                """
                {
                    "syncedInstanceURI": "%s"
                }
                """, importUri);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + eloquaToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(syncBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://secure.p04.eloqua.com/api/bulk/2.0/syncs",
                HttpMethod.POST,
                requestEntity,
                String.class);

        // Manejo de la respuesta del tercer POST de sincronización
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Sincronización exitosa");
        } else {
            System.out.println("Error en la sincronización: " + response.getBody());
        }
    }

}

