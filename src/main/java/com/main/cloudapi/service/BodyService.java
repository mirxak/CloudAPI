package com.main.cloudapi.service;

import com.main.cloudapi.dao.BodyDAO;
import com.main.cloudapi.entity.Body;
import com.main.cloudapi.service.base.BaseComplectationService;
import com.main.cloudapi.utils.JsonUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mirxak on 01.02.15.
 */
@Service
public class BodyService implements BaseComplectationService<Body> {

    @Autowired
    BodyDAO bodyDAO;

    @Override
    public Body getById(Long id){
        return bodyDAO.getById(id, true);
    }

    @Override
    @Transactional
    public Body add(String json){
        Body body = JsonUtils.getFromJson(json, Body.class, true);
        bodyDAO.add(body);
        return body;
    }

    @Override
    @Transactional
    public Body edit(Long id, String json){
        Body body = bodyDAO.getById(id, true);
        JsonUtils.update(json, body, true);
        return bodyDAO.update(body);
    }

    @Override
    @Transactional
    public Body delete(Long id){
        Body body = bodyDAO.getById(id, true);
        bodyDAO.delete(body);
        return body;
    }


}
