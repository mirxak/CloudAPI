package com.main.cloudapi.controller;

import com.main.cloudapi.api.CarServiceControllerI;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.CarService;
import com.main.cloudapi.service.ServiceCarService;
import com.main.cloudapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by mirxak on 15.03.15.
 */
@Controller
public class CarServiceController extends BaseController implements CarServiceControllerI {

    @Autowired
    ServiceCarService serviceCarService;

    @Autowired
    UserService userService;

    @Override
    public CarService getCarService(@PathVariable String id) {
        return serviceCarService.getCarService(parseID(id));
    }

    @Override
    public List<CarService> getCarServices() {
        return serviceCarService.getCarServices();
    }

    @Override
    public CarService addCarService(@RequestBody String json,
                                    @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return serviceCarService.addCarService(json);
    }

    @Override
    public CarService editCarService(@PathVariable String id, @RequestBody String json,
                                     @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return serviceCarService.editCarService(parseID(id), json);
    }

    @Override
    public CarService deleteCarService(@PathVariable String id,
                                       @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return serviceCarService.deleteCarService(parseID(id));
    }
}
