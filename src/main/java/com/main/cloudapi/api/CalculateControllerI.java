package com.main.cloudapi.api;

/**
 * Created by mirxak on 15.03.15.
 */

import com.main.cloudapi.service.CalculateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"}, value = "/calculates")
public interface CalculateControllerI {

    @RequestMapping(value = "/service", method = RequestMethod.POST)
    @ResponseBody
    public Float calculateService(@RequestBody String json);

    @RequestMapping(value = "/osago", method = RequestMethod.POST)
    @ResponseBody
    public Float calculateOSAGO(@RequestBody String json);

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    @ResponseBody
    public List<CalculateService.CalculateResult> calculateMain(@RequestBody String json);

}
