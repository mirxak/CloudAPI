package com.main.cloudapi.controller;

import com.main.cloudapi.api.SafetyControllerI;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Safety;
import com.main.cloudapi.service.SafetyService;
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
public class SafetyController extends BaseController implements SafetyControllerI {

    @Autowired
    SafetyService safetyService;

    @Autowired
    UserService userService;

    @Override
    public Safety getSafety(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return safetyService.getById(parseID(id));
    }

    @Override
    public Safety addSafety(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @RequestBody String json,
                            @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return safetyService.add(json);
    }

    @Override
    public Safety editSafety(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id, @RequestBody String json,
                             @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return safetyService.edit(parseID(id), json);
    }

    @Override
    public Safety deleteSafety(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id,
                               @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return safetyService.delete(parseID(id));
    }
}
