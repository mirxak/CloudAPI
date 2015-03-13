package com.main.cloudapi.api;

/**
 * Created by mirxak on 02.02.15.
 */

import com.main.cloudapi.entity.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"},
        value = "/" + Brand.CATEGORY + "/{brandID}/" + Car.CATEGORY + "/{carID}/" +
                Complectation.CATEGORY + "/{cID}/" + EngineGearbox.CATEGORY)
public interface EngineControllerI {

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public EngineGearbox getEngineGearbox(@PathVariable String brandID, @PathVariable String carID,
                        @PathVariable String cID, @PathVariable String id);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public EngineGearbox addEngineGearbox(@PathVariable String brandID, @PathVariable String carID,
                        @PathVariable String cID, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    @ResponseBody
    public EngineGearbox editEngineGearbox(@PathVariable String brandID, @PathVariable String carID,
                         @PathVariable String cID, @PathVariable String id, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public EngineGearbox deleteEngineGearbox(@PathVariable String brandID, @PathVariable String carID,
                           @PathVariable String cID, @PathVariable String id);

}
