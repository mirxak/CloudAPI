package com.main.cloudapi.controller;

import com.main.cloudapi.api.CarServiceControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.CarService;
import com.main.cloudapi.service.ServiceCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by mirxak on 15.03.15.
 */
@Controller
public class CarServiceController extends BaseController implements CarServiceControllerI {

    @Autowired
    ServiceCarService serviceCarService;

    @Override
    public CarService getCarService(@PathVariable String id) {
        return serviceCarService.getCarService(parseID(id));
    }

    @Override
    public List<CarService> getCarServices() {
        return serviceCarService.getCarServices();
    }

    @Override
    public CarService addCarService(@RequestBody String json) {
        return serviceCarService.addCarService(json);
    }

    @Override
    public CarService editCarService(@PathVariable String id, @RequestBody String json) {
        return serviceCarService.editCarService(parseID(id), json);
    }

    @Override
    public CarService deleteCarService(@PathVariable String id) {
        return serviceCarService.deleteCarService(parseID(id));
    }
}
