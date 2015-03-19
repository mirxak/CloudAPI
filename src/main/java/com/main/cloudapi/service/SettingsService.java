package com.main.cloudapi.service;

import com.main.cloudapi.dao.SettingsDAO;
import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.Settings;
import com.main.cloudapi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mirxak on 20.03.15.
 */
@Service
public class SettingsService {

    @Autowired
    SettingsDAO settingsDAO;

    public Settings getById(Long id){
        return settingsDAO.getById(id, true);
    }

    public List<Settings> getAll(){
        return settingsDAO.getAll(BaseDAO.STD_OFFSET, BaseDAO.STD_LIMIT);
    }

    @Transactional
    public Settings addSetting(String json){
        Settings settings = JsonUtils.getFromJson(json, Settings.class, true);
        settingsDAO.add(settings);
        return settings;
    }

    @Transactional
    public Settings editSetting(Long id, String json){
        Settings settings = getById(id);
        JsonUtils.update(json, settings, true);
        return settingsDAO.update(settings);
    }

    @Transactional
    public Settings deleteSetting(Long id){
        Settings settings = getById(id);
        settingsDAO.delete(settings);
        return settings;
    }

}
