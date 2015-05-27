package com.main.cloudapi.api;

import com.main.cloudapi.entity.Body;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.entity.Complectation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mirxak on 01.02.15.
 */
@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"},
                value = "/" + Brand.CATEGORY + "/{brandID}/" + Car.CATEGORY + "/{carID}/" +
                        Complectation.CATEGORY + "/{cID}/" + Body.CATEGORY)
public interface BodyControllerI {

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Body getBody(@PathVariable String brandID, @PathVariable String carID,
                         @PathVariable String cID, @PathVariable String id);

    // Уточнить, нужен ли такой метод
//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public List<Brand> getBodies();

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Body addBody(@PathVariable String brandID, @PathVariable String carID,
                         @PathVariable String cID, @RequestBody String json,
                         @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Body editBody(@PathVariable String brandID, @PathVariable String carID,
                          @PathVariable String cID, @PathVariable String id, @RequestBody String json,
                          @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Body deleteBody(@PathVariable String brandID, @PathVariable String carID,
                            @PathVariable String cID, @PathVariable String id,
                            @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

}
