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

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    @ResponseBody
    public CalculateService.FrontendResult calculateMain(@RequestBody String json);

    @RequestMapping(value = "/main/user-complectations", method = RequestMethod.POST)
    @ResponseBody
    public CalculateService.FrontendResult calculateUserComplectations(@RequestBody String json);

}
