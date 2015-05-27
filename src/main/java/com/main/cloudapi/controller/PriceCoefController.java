package com.main.cloudapi.controller;

import com.main.cloudapi.api.PriceCoefControllerI;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.PriceCoef;
import com.main.cloudapi.service.PriceCoefService;
import com.main.cloudapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by mirxak on 22.03.15.
 */
@Controller
public class PriceCoefController extends BaseController implements PriceCoefControllerI {

    @Autowired
    PriceCoefService priceCoefService;

    @Autowired
    UserService userService;

    @Override
    public PriceCoef getPriceCoef(@PathVariable String id) {
        return priceCoefService.getById(parseID(id));
    }

    @Override
    public List<PriceCoef> getPriceCoefs() {
        return priceCoefService.getAll();
    }

    @Override
    public PriceCoef addPriceCoef(@RequestBody String json,
                                  @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return priceCoefService.add(json);
    }

    @Override
    public PriceCoef editPriceCoef(@PathVariable String id, @RequestBody String json,
                                   @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return priceCoefService.edit(parseID(id), json);
    }

    @Override
    public PriceCoef deletePriceCoef(@PathVariable String id,
                                     @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return priceCoefService.delete(parseID(id));
    }
}
