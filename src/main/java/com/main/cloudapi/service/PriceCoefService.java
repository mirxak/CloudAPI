package com.main.cloudapi.service;

import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.dao.PriceCoefDAO;
import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.PriceCoef;
import com.main.cloudapi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mirxak on 22.03.15.
 */
@Service
public class PriceCoefService {

    @Autowired
    PriceCoefDAO priceCoefDAO;

    public PriceCoef getById(Long id){
        return priceCoefDAO.getById(id, true);
    }

    public List<PriceCoef> getAll(){
        return priceCoefDAO.getAll(BaseDAO.STD_OFFSET, BaseDAO.STD_LIMIT);
    }

    public PriceCoefDAO getDAO(){
        return priceCoefDAO;
    }

    @Transactional
    public PriceCoef add(String json){
        PriceCoef priceCoef = JsonUtils.getFromJson(json, PriceCoef.class, true);
        if (priceCoef.getPriceMin() > priceCoef.getPriceMax()){
            throw new ThrowFabric.BadRequestException("price_min > price_max");
        }
        priceCoefDAO.add(priceCoef);
        return priceCoef;
    }

    @Transactional
    public PriceCoef edit(Long id, String json){
        PriceCoef priceCoef = getById(id);
        PriceCoef edt = JsonUtils.getFromJson(json, PriceCoef.class, true);
        if (edt.getPriceMin() > edt.getPriceMax()){
            throw new ThrowFabric.BadRequestException("price_min > price_max");
        }
        JsonUtils.update(json, priceCoef, true);
        return priceCoefDAO.update(priceCoef);
    }

    @Transactional
    public PriceCoef delete(Long id){
        PriceCoef priceCoef = getById(id);
        priceCoefDAO.delete(priceCoef);
        return priceCoef;
    }

}
