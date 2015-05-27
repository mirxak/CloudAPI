package com.main.cloudapi.controller;

import com.main.cloudapi.api.CarControllerI;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.service.CarService;
import com.main.cloudapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@Controller
public class CarController extends BaseController implements CarControllerI {

    @Autowired
    CarService carService;

    @Autowired
    UserService userService;

    @Override
    public Car getCar(@PathVariable String brandID, @PathVariable String id) {
        return carService.getById(parseID(brandID), parseID(id));
    }

    @Override
    public List<Car> getCars(@PathVariable String brandID) {
        return carService.getCarsOfBrand(parseID(brandID));
    }

    @Override
    public Car addCar(@PathVariable String brandID, @RequestBody String json,
                      @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return carService.addCar(parseID(brandID), json);
    }

    @Override
    public Car editCar(@PathVariable String brandID, @PathVariable String id, @RequestBody String json,
                       @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return carService.editCar(parseID(brandID), parseID(id), json);
    }

    @Override
    public Car deleteCar(@PathVariable String brandID, @PathVariable String id,
                         @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return carService.deleteCar(parseID(brandID), parseID(id));
    }

}
