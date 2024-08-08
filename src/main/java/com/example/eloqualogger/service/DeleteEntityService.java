package com.example.eloqualogger.service;

import com.example.eloqualogger.model.DeleteEntity;
import com.example.eloqualogger.repository.DeleteEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeleteEntityService {

    @Autowired
    private DeleteEntityRepository repository;

    public DeleteEntity createDeleteEntity(Map<String, Object> data){
        DeleteEntity newDeleteEntity = new DeleteEntity(data);
        return repository.save(newDeleteEntity);
    }

    public List<DeleteEntity> getAllDeleteEntities (){
        return repository.findAll();
    }
}
