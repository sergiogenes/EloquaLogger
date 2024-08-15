package com.example.eloqualogger.controller;

import com.example.eloqualogger.model.NotificationEntity;
import com.example.eloqualogger.service.NotificationEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/notification_entities")
public class NotificationEntityController {

    @Autowired
    private NotificationEntityService service;

    @PostMapping
    public ResponseEntity<String> createNotificationEntity(@RequestParam Map<String, Object> queryStrings, @RequestBody Map<String, Object> body){

        Map<String, Object> newNotificationEntity = new HashMap<>();

        newNotificationEntity.put("queryStrings", queryStrings);
        newNotificationEntity.put("body", body);
        service.createNotificationEntity(newNotificationEntity);

        String htmlResponse = "<tr><td><a href=\"www.google.com\">Sergio.Milla</a></td><td>77860</td><td>$ 10.000.000</td></tr>";
        return ResponseEntity.ok(htmlResponse);
    }

    @GetMapping
    public List<NotificationEntity> getAllNotificationEntities(){
        return service.getAllNotificationEntities();
    }
}
