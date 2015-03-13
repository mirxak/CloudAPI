package com.main.cloudapi.api;

/**
 * Created by mirxak on 02.02.15.
 */

import com.main.cloudapi.entity.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"},
        value = "/" + Brand.CATEGORY + "/{brandID}/" + Car.CATEGORY + "/{carID}/" +
                Complectation.CATEGORY + "/{cID}/" + Comfort.CATEGORY)
public interface ComfortControllerI {

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Comfort getComfort(@PathVariable String brandID, @PathVariable String carID,
                                          @PathVariable String cID, @PathVariable String id);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Comfort addComfort(@PathVariable String brandID, @PathVariable String carID,
                                          @PathVariable String cID, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Comfort editComfort(@PathVariable String brandID, @PathVariable String carID,
                                           @PathVariable String cID, @PathVariable String id, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Comfort deleteComfort(@PathVariable String brandID, @PathVariable String carID,
                                             @PathVariable String cID, @PathVariable String id);

}
