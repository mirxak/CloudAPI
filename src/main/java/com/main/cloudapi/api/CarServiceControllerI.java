package com.main.cloudapi.api;

import com.main.cloudapi.entity.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mirxak on 15.03.15.
 */
@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"}, value = "/" + CarService.CATEGORY)
public interface CarServiceControllerI {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CarService getCarService(@PathVariable String id);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<CarService> getCarServices();

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CarService addCarService(@RequestBody String json);

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CarService editCarService(@PathVariable String id, @RequestBody String json);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CarService deleteCarService(@PathVariable String id);
}
