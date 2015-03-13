package com.main.cloudapi.service;

import com.main.cloudapi.dao.EngineDAO;
import com.main.cloudapi.entity.EngineGearbox;
import com.main.cloudapi.service.base.BaseComplectationService;
import com.main.cloudapi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mirxak on 07.02.15.
 */
@Service
public class EngineService implements BaseComplectationService<EngineGearbox> {

    @Autowired
    EngineDAO engineDAO;


    @Override
    public EngineGearbox getById(Long id) {
        return engineDAO.getById(id, true);
    }

    @Override
    @Transactional
    public EngineGearbox add(String json) {
        EngineGearbox engineGearbox = JsonUtils.getFromJson(json, EngineGearbox.class, true);
        engineDAO.add(engineGearbox);
        return engineGearbox;
    }

    @Override
    @Transactional
    public EngineGearbox edit(Long id, String json) {
        EngineGearbox engineGearbox = getById(id);
        JsonUtils.update(json, engineGearbox, true);
        return engineDAO.update(engineGearbox);
    }

    @Override
    @Transactional
    public EngineGearbox delete(Long id) {
        EngineGearbox engineGearbox = getById(id);
        engineDAO.delete(engineGearbox);
        return engineGearbox;
    }
}
