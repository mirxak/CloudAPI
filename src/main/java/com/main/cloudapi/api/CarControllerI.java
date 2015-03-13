package com.main.cloudapi.api;

import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.Car;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"},
                value = "/" + Brand.CATEGORY + "/{brandID}/" + Car.CATEGORY)
public interface CarControllerI {

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Car getCar(@PathVariable String brandID, @PathVariable String id);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Car> getCars(@PathVariable String brandID);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Car addCar(@PathVariable String brandID, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Car editCar(@PathVariable String brandID, @PathVariable String id, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Car deleteCar(@PathVariable String brandID, @PathVariable String id);

}
