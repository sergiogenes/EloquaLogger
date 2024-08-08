package com.example.eloqualogger.repository;

import com.example.eloqualogger.model.ConfigureEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigureEntityRepository extends MongoRepository<ConfigureEntity, String > {
}
