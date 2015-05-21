package com.main.cloudapi.service;

import com.main.cloudapi.dao.CarDAO;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.Car;
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
public class CarService {

    @Autowired
    CarDAO carDAO;

    @Autowired
    BrandService brandService;

    public CarDAO getDAO(){
        return carDAO;
    }

    public List<Car> getCarsOfBrand(Long brandID){
        List<Car> cars = carDAO.getAll(carDAO.STD_OFFSET, carDAO.STD_LIMIT, carDAO.createCriteria(brandID));
        if ((cars == null) || (cars.isEmpty())){
            return Collections.emptyList();
        }
        return cars;
    }

    public Car getById(Long brandID, Long id){
        Criteria criteria = carDAO.createCriteria(brandID);
        return carDAO.getById(id, true, criteria);
    }

    @Transactional
    public Car addCar(Long brandID, String json){
        Brand brand = brandService.getById(brandID);
        Car car = JsonUtils.getFromJson(json, Car.class, true);
        car.setBrand(brand);
        carDAO.add(car);
        return car;
    }

    @Transactional
    public Car editCar(Long brandID, Long id, String json){
        Car car = getById(brandID, id);
        Car edtCar = JsonUtils.getFromJson(json, Car.class, true);
        Brand brand = car.getBrand();
        if (!car.getBrandId().equals(edtCar.getBrandId())){
            brand = brandService.getById(edtCar.getBrandId());
        }
        JsonUtils.update(json, car, true);
        car.setBrand(brand);
        return carDAO.update(car);
    }

    @Transactional
    public Car deleteCar(Long brandID, Long id){
        Brand brand = brandService.getById(brandID);
        Car car = getById(brandID, id);
        carDAO.delete(car);
        return car;
    }

    @Transactional
    public Car simpleUpdate(Car car){
        return carDAO.update(car);
    }
}
