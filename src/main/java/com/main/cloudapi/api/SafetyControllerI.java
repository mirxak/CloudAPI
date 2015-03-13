package com.main.cloudapi.api;

import com.main.cloudapi.entity.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mirxak on 02.02.15.
 */
@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"},
        value = "/" + Brand.CATEGORY + "/{brandID}/" + Car.CATEGORY + "/{carID}/" +
                Complectation.CATEGORY + "/{cID}/" + Safety.CATEGORY)
public interface SafetyControllerI {

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Safety getSafety(@PathVariable String brandID, @PathVariable String carID,
                                          @PathVariable String cID, @PathVariable String id);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Safety addSafety(@PathVariable String brandID, @PathVariable String carID,
                                          @PathVariable String cID, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Safety editSafety(@PathVariable String brandID, @PathVariable String carID,
                                           @PathVariable String cID, @PathVariable String id, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Safety deleteSafety(@PathVariable String brandID, @PathVariable String carID,
                                             @PathVariable String cID, @PathVariable String id);
    
}
