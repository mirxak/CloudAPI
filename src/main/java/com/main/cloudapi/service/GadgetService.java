package com.main.cloudapi.service;

import com.main.cloudapi.dao.GadgetDAO;
import com.main.cloudapi.entity.Gadget;
import com.main.cloudapi.service.base.BaseComplectationService;
import com.main.cloudapi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mirxak on 07.02.15.
 */
@Service
public class GadgetService implements BaseComplectationService<Gadget> {

    @Autowired
    GadgetDAO gadgetDAO;

    @Override
    public Gadget getById(Long id) {
        return gadgetDAO.getById(id);
    }

    @Override
    @Transactional
    public Gadget add(String json) {
        Gadget gadget = JsonUtils.getFromJson(json, Gadget.class, true);
        gadgetDAO.add(gadget);
        return gadget;
    }

    @Override
    @Transactional
    public Gadget edit(Long id, String json) {
        Gadget gadget = getById(id);
        JsonUtils.update(json, gadget, true);
        return gadgetDAO.update(gadget);
    }

    @Override
    @Transactional
    public Gadget delete(Long id) {
        Gadget gadget = getById(id);
        gadgetDAO.delete(gadget);
        return gadget;
    }
}
