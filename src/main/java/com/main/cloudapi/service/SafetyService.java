package com.main.cloudapi.service;

import com.main.cloudapi.dao.SafetyDAO;
import com.main.cloudapi.entity.Safety;
import com.main.cloudapi.service.base.BaseComplectationService;
import com.main.cloudapi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mirxak on 07.02.15.
 */
@Service
public class SafetyService implements BaseComplectationService<Safety> {

    @Autowired
    SafetyDAO safetyDAO;

    @Override
    public Safety getById(Long id) {
        return safetyDAO.getById(id);
    }

    @Override
    @Transactional
    public Safety add(String json) {
        Safety safety = JsonUtils.getFromJson(json, Safety.class, true);
        safetyDAO.add(safety);
        return safety;
    }

    @Override
    @Transactional
    public Safety edit(Long id, String json) {
        Safety safety = getById(id);
        JsonUtils.update(json, safety, true);
        return safetyDAO.update(safety);
    }

    @Override
    @Transactional
    public Safety delete(Long id) {
        Safety safety = getById(id);
        safetyDAO.delete(safety);
        return safety;
    }
}
