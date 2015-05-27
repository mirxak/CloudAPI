package com.main.cloudapi.controller;

import com.main.cloudapi.api.ComfortControllerI;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Comfort;
import com.main.cloudapi.service.ComfortService;
import com.main.cloudapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by mirxak on 02.02.15.
 */
@Controller
public class ComfortController extends BaseController implements ComfortControllerI {

    @Autowired
    ComfortService comfortService;

    @Autowired
    UserService userService;


    @Override
    public Comfort getComfort(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return comfortService.getById(parseID(id));
    }

    @Override
    public Comfort addComfort(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @RequestBody String json,
                              @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return comfortService.add(json);
    }

    @Override
    public Comfort editComfort(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id, @RequestBody String json,
                               @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return comfortService.edit(parseID(id), json);
    }

    @Override
    public Comfort deleteComfort(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id,
                                 @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return comfortService.delete(parseID(id));
    }
}
