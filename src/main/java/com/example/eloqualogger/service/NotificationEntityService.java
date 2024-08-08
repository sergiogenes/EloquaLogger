package com.example.eloqualogger.service;

import com.example.eloqualogger.model.NotificationEntity;
import com.example.eloqualogger.repository.NotificationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationEntityService {

    @Autowired
    private NotificationEntityRepository repository;

    public NotificationEntity createNotificationEntity(Map<String, Object> data){
        NotificationEntity newNotificationEntity = new NotificationEntity(data);
        return repository.save(newNotificationEntity);
    }

    public List<NotificationEntity> getAllNotificationEntities(){
        return repository.findAll();
    }
}
