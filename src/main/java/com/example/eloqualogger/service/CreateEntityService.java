package com.example.eloqualogger.service;

import com.example.eloqualogger.model.CreateEntity;
import com.example.eloqualogger.repository.CreateEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CreateEntityService {

    @Autowired
    private CreateEntityRepository repository;

    public CreateEntity createCreateEntity(Map<String, Object> data){
        CreateEntity newCreatedEntity = new CreateEntity(data);
        return repository.save(newCreatedEntity);
    }

    public List<CreateEntity> getAllCreateEntities (){
        return repository.findAll();
    }
}
