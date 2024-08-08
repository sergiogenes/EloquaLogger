package com.example.eloqualogger.service;

import com.example.eloqualogger.model.ConfigureEntity;
import com.example.eloqualogger.repository.ConfigureEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ConfigureEntityService {

    @Autowired
    private ConfigureEntityRepository repository;

    public ConfigureEntity createConfigureEntity (Map<String, Object> data){
        ConfigureEntity newConfigureEntity = new ConfigureEntity(data);
        return repository.save(newConfigureEntity);
    }

    public List<ConfigureEntity> getAllConfireEntities(){
        return repository.findAll();
    }
}
