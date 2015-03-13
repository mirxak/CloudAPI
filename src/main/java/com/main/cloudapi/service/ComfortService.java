package com.main.cloudapi.service;

import com.main.cloudapi.dao.ComfortDAO;
import com.main.cloudapi.entity.Comfort;
import com.main.cloudapi.service.base.BaseComplectationService;
import com.main.cloudapi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mirxak on 07.02.15.
 */
@Service
public class ComfortService implements BaseComplectationService<Comfort> {

    @Autowired
    ComfortDAO comfortDAO;

    @Override
    public Comfort getById(Long id){
        return comfortDAO.getById(id, true);
    }

    @Override
    @Transactional
    public Comfort add(String json){
        Comfort comfort = JsonUtils.getFromJson(json, Comfort.class, true);
        comfortDAO.add(comfort);
        return comfort;
    }

    @Override
    @Transactional
    public Comfort edit(Long id, String json){
        Comfort comfort = getById(id);
        JsonUtils.update(json, comfort, true);
        return comfortDAO.update(comfort);
    }

    @Override
    @Transactional
    public Comfort delete(Long id){
        Comfort comfort = getById(id);
        comfortDAO.delete(comfort);
        return comfort;
    }

}
