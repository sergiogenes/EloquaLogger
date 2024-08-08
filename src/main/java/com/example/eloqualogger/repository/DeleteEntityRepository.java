package com.example.eloqualogger.repository;

import com.example.eloqualogger.model.DeleteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeleteEntityRepository extends MongoRepository<DeleteEntity, String> {
}
