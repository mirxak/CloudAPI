package com.main.cloudapi.controller;

import com.main.cloudapi.api.ComplectationControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.Complectation;
import com.main.cloudapi.entity.views.ComplectationView;
import com.main.cloudapi.service.ComplectationService;
import com.main.cloudapi.utils.JsonWebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@Controller
public class ComplectationController extends BaseController implements ComplectationControllerI {

    @Autowired
    ComplectationService complectationService;

    @Override
    public Complectation getComplectation(@PathVariable String brandID, @PathVariable String carID, @PathVariable String id) {
        JsonWebContext.addMapperView(ComplectationView.ComplectationCar.class);
        return complectationService.getById(parseID(brandID), parseID(carID), parseID(id));
    }

    @Override
    public List<Complectation> getComplectations(@PathVariable String brandID, @PathVariable String carID) {
        JsonWebContext.addMapperView(ComplectationView.ComplectationCar.class);
        return complectationService.getComplectations(parseID(brandID), parseID(carID));
    }

    @Override
    public Complectation addComplectation(@PathVariable String brandID, @PathVariable String carID, @RequestBody String json) {
        return complectationService.addComplectation(parseID(brandID), parseID(carID), json);
    }

    @Override
    public Complectation editComplectation(@PathVariable String brandID, @PathVariable String carID, @PathVariable String id, @RequestBody String json) {
        return complectationService.editComplectation(parseID(brandID), parseID(carID), parseID(id), json);
    }

    @Override
    public Complectation deleteComplectation(@PathVariable String brandID, @PathVariable String carID, @PathVariable String id) {
        return complectationService.deleteComplectation(parseID(brandID), parseID(carID), parseID(id));
    }
}
