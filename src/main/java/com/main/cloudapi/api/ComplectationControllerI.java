package com.main.cloudapi.api;

import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.entity.Complectation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"},
        value = "/" + Brand.CATEGORY + "/{brandID}/" + Car.CATEGORY + "/{carID}/" + Complectation.CATEGORY)
public interface ComplectationControllerI {

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Complectation getComplectation(@PathVariable String brandID, @PathVariable String carID, @PathVariable String id);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Complectation> getComplectations(@PathVariable String brandID, @PathVariable String carID);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Complectation addComplectation(@PathVariable String brandID, @PathVariable String carID, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Complectation editComplectation(@PathVariable String brandID, @PathVariable String carID, @PathVariable String id, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Complectation deleteComplectation(@PathVariable String brandID, @PathVariable String carID, @PathVariable String id);


}
