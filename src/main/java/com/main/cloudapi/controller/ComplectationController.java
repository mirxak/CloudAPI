package com.main.cloudapi.controller;

import com.main.cloudapi.api.ComplectationControllerI;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.Complectation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@Controller
public class ComplectationController implements ComplectationControllerI {
    @Override
    public Complectation getComplectation(@PathVariable String brandID, @PathVariable String carID, @PathVariable String id) {
        return null;
    }

    @Override
    public List<Complectation> getComplectations(@PathVariable String brandID, @PathVariable String carID) {
        return null;
    }

    @Override
    public Complectation addComplectation(@PathVariable String brandID, @PathVariable String carID, @RequestBody String json) {
        return null;
    }

    @Override
    public Complectation editComplectation(@PathVariable String brandID, @PathVariable String carID, @PathVariable String id, @RequestBody String json) {
        return null;
    }

    @Override
    public Complectation deleteComplectation(@PathVariable String brandID, @PathVariable String carID, @PathVariable String id) {
        return null;
    }
}
