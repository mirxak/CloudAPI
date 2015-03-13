package com.main.cloudapi.service;

import com.main.cloudapi.dao.ComplectationDAO;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.entity.Complectation;
import com.main.cloudapi.utils.JsonUtils;
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
        Car car = carService.getById(brandID, carID);
        return complectationDAO.getById(id, true, complectationDAO.createCriteria(carID));
    }

    @Transactional
    public Complectation addComplectation(Long brandID, Long carID, String json){
        Car car = carService.getById(brandID, carID);
        Complectation complectation = JsonUtils.getFromJson(json, Complectation.class, true);
        complectation.setCar(car);
        // добавляем все остальные составляющие комплектации, если они не пустые

        return null;
    }

}
