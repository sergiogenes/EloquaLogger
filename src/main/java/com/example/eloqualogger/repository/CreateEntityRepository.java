package com.example.eloqualogger.repository;

import com.example.eloqualogger.model.CreateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreateEntityRepository extends MongoRepository<CreateEntity, String> {
}
