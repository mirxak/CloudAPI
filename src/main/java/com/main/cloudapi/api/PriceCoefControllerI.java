package com.main.cloudapi.api;

/**
 * Created by mirxak on 22.03.15.
 */

import com.main.cloudapi.entity.PriceCoef;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"}, value = PriceCoef.CATEGORY)
public interface PriceCoefControllerI {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PriceCoef getPriceCoef(@PathVariable String id);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<PriceCoef> getPriceCoefs();

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public PriceCoef addPriceCoef(@RequestBody String json,
                                  @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public PriceCoef editPriceCoef(@PathVariable String id, @RequestBody String json,
                                   @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public PriceCoef deletePriceCoef(@PathVariable String id,
                                     @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);
}
