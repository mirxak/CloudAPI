package com.main.cloudapi.service;

import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.dao.CarServiceDAO;
import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.CarService;
import com.main.cloudapi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mirxak on 15.03.15.
 */
@Service
public class ServiceCarService {

    @Autowired
    CarServiceDAO carServiceDAO;

    public CarService getCarService(Long id){
        return carServiceDAO.getById(id, true);
    }

    public List<CarService> getCarServices(){
        return carServiceDAO.getAll(BaseDAO.STD_OFFSET, BaseDAO.STD_LIMIT);
    }

    @Transactional
    public CarService addCarService(String json){
        CarService carService = JsonUtils.getFromJson(json, CarService.class, true);
        if (carService.getPriceMin() > carService.getPriceMax()){
            throw new ThrowFabric.BadRequestException("price_min > price_max");
        }
        carServiceDAO.add(carService);
        return carService;
    }

    @Transactional
    public CarService editCarService(Long id, String json){
        CarService carService = getCarService(id);
        CarService edtServ = JsonUtils.getFromJson(json, CarService.class, true);
        if (edtServ.getPriceMin() > edtServ.getPriceMax()){
            throw new ThrowFabric.BadRequestException("price_min > price_max");
        }
        JsonUtils.update(json, carService, true);
        return carServiceDAO.update(carService);
    }

    @Transactional
    public CarService deleteCarService(Long id){
        CarService carService = getCarService(id);
        carServiceDAO.delete(carService);
        return carService;
    }

}
