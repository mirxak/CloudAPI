package com.main.cloudapi.controller;

import com.main.cloudapi.api.BodyControllerI;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Body;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.service.BodyService;
import com.main.cloudapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by mirxak on 23.01.15.
 */
@Controller
public class BodyController extends BaseController implements BodyControllerI {

    @Autowired
    BodyService bodyService;

    @Autowired
    UserService userService;

    @Override
    public Body getBody(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return bodyService.getById(parseID(id));
    }

    @Override
    public Body addBody(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @RequestBody String json,
                        @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return bodyService.add(json);
    }

    @Override
    public Body editBody(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id, @RequestBody String json,
                         @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return bodyService.edit(parseID(id), json);
    }

    @Override
    public Body deleteBody(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id,
                           @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return bodyService.delete(parseID(id));
    }
}
