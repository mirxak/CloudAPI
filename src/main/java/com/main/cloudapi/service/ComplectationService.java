package com.main.cloudapi.service;

import com.main.cloudapi.dao.ComplectationDAO;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.entity.Complectation;
import com.main.cloudapi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by mirxak on 01.02.15.
 */
@Service
public class ComplectationService {

    @Autowired
    ComplectationDAO complectationDAO;

    @Autowired
    CarService carService;

    @Autowired
    EngineService engineService;

    @Autowired
    BodyService bodyService;

    @Autowired
    SafetyService safetyService;

    @Autowired
    GadgetService gadgetService;

    @Autowired
    ComfortService comfortService;

    public List<Complectation> getComplectations(Long brandID, Long carID){
        Car car = carService.getById(brandID, carID);
        List<Complectation> complectations = complectationDAO.getAll(complectationDAO.STD_OFFSET, complectationDAO.STD_LIMIT,
                                                                     complectationDAO.createCriteria(carID));
        if ((complectations == null) || (complectations.isEmpty())){
            return Collections.emptyList();
        }
        return complectations;
    }

    public Complectation getById(Long brandID, Long carID, Long id){
        carService.getById(brandID, carID);
        return complectationDAO.getById(id, true, complectationDAO.createCriteria(carID));
    }

    @Transactional
    public Complectation addComplectation(Long brandID, Long carID, String json){
        Car car = carService.getById(brandID, carID);
        Complectation complectation = JsonUtils.getFromJson(json, Complectation.class, true);
        complectation.setCar(car);
        complectation.setEngineGearbox(engineService.getById(complectation.getEngineId()));
        complectation.setBody(bodyService.getById(complectation.getBodyId()));
        complectation.setComfort(comfortService.getById(complectation.getComfortId()));
        complectation.setSafety(safetyService.getById(complectation.getSafetyId()));
        complectation.setGadget(gadgetService.getById(complectation.getGadgetsId()));

        // добавляем все остальные составляющие комплектации, если они не пустые
        complectationDAO.add(complectation);
        return complectation;
    }

    @Transactional
    public Complectation editComplectation(Long brandID, Long carID, Long id, String json){
        Complectation complectation = getById(brandID, carID, id);
        Complectation edtCmpl = JsonUtils.getFromJson(json, Complectation.class, true);

        if (!StringUtils.isBlank(edtCmpl.getName())){
            complectation.setName(edtCmpl.getName());
        }
        if (edtCmpl.getPrice() != null){
            complectation.setPrice(edtCmpl.getPrice());
        }
        if (edtCmpl.getCo2() != null){
            complectation.setCo2(edtCmpl.getCo2());
        }

        return complectationDAO.update(complectation);
    }

    @Transactional
    public Complectation deleteComplectation(Long brandID, Long carID, Long id){
        Complectation complectation = getById(brandID, carID, id);
        complectationDAO.delete(complectation);
        return complectation;
    }

}
