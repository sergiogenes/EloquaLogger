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

    public String getConfigForm() {
        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>Configure Entity Form</title>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
                "h2 { color: #333; }" +
                "form { background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); max-width: 400px; margin: auto; }" +
                "label { font-weight: bold; display: block; margin-bottom: 10px; }" +
                "input[type='text'] { width: 100%; padding: 8px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 4px; }" +
                "button { background-color: #4CAF50; color: white; padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer; }" +
                "button:hover { background-color: #45a049; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h2>Configure Entity Form</h2>" +
                "<form action='https://facturacionteco-amcuhphjh0bbaeha.eastus-01.azurewebsites.net/api/configure_entities" +
                "?instance_id={InstanceId}" +
                "&install_id={InstallId}" +
                "&user_name={UserName}" +
                "&user_id={UserId}" +
                "&site_name={SiteName}" +
                "&site_id={SiteId}" +
                "&app_id={AppId}" +
                "&asset_id={AssetId}" +
                "&asset_type={AssetType}" +
                "&asset_name={AssetName}'" +
                " method='post'>" +
                "<label for='name'>Nombre:</label>" +
                "<input type='text' id='name' name='name'>" +
                "<button type='submit'>Enviar</button>" +
                "</form>" +
                "</body>" +
                "</html>";
    }
}
