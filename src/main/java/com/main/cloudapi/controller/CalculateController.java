package com.main.cloudapi.controller;

import com.main.cloudapi.api.CalculateControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.service.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by mirxak on 15.03.15.
 */
@Controller
public class CalculateController extends BaseController implements CalculateControllerI {

    @Autowired
    CalculateService calculateService;

    @Override
    public CalculateService.FrontendResult calculateMain(@RequestBody String json) {
        CalculateService.FrontendResult frontendResult = new CalculateService.FrontendResult();
        frontendResult.calculateResults = calculateService.calculateMain(json);
        return frontendResult ;
    }

    @Override
    public CalculateService.FrontendResult calculateUserComplectations(@RequestBody String json) {
        CalculateService.FrontendResult frontendResult = new CalculateService.FrontendResult();
        frontendResult.calculateResults = calculateService.calculateUserComplectations(json);
        return frontendResult;
    }

}
