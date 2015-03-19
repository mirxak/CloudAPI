package com.main.cloudapi.controller;

import com.main.cloudapi.api.CalculateControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.service.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by mirxak on 15.03.15.
 */
@Controller
public class CalculateController extends BaseController implements CalculateControllerI {

    @Autowired
    CalculateService calculateService;

    @Override
    public Float calculateService(@RequestBody String json) {
        return null;
    }

    @Override
    public Float calculateOSAGO(@RequestBody String json) {
        return null;
    }

}
