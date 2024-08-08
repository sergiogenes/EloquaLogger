package com.example.eloqualogger.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "delete_entities")
public class DeleteEntity {

    @Id
    private String id;
    private Map<String, Object> data;

    public DeleteEntity(Map<String, Object> data){
        this.data = data;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
