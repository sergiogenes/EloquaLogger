package com.example.eloqualogger.repository;

import com.example.eloqualogger.model.NotificationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationEntityRepository extends MongoRepository<NotificationEntity, String> {
}
