package com.main.cloudapi.controller;

import com.main.cloudapi.api.EngineControllerI;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.EngineGearbox;
import com.main.cloudapi.service.EngineService;
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
public class EngineController extends BaseController implements EngineControllerI {

    @Autowired
    EngineService engineService;

    @Autowired
    UserService userService;

    @Override
    public EngineGearbox getEngineGearbox(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return engineService.getById(parseID(id));
    }

    @Override
    public EngineGearbox addEngineGearbox(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @RequestBody String json,
                                          @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return engineService.add(json);
    }

    @Override
    public EngineGearbox editEngineGearbox(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id, @RequestBody String json,
                                           @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return engineService.edit(parseID(id), json);
    }

    @Override
    public EngineGearbox deleteEngineGearbox(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id,
                                             @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return engineService.delete(parseID(id));
    }
}
