package com.main.cloudapi.controller;

import com.main.cloudapi.api.GadgetsControllerI;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Gadget;
import com.main.cloudapi.service.GadgetService;
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
public class GadgetsController extends BaseController implements GadgetsControllerI {

    @Autowired
    GadgetService gadgetService;

    @Autowired
    UserService userService;

    @Override
    public Gadget getGadget(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id) {
        return gadgetService.getById(parseID(id));
    }

    @Override
    public Gadget addGadget(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @RequestBody String json,
                            @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return gadgetService.add(json);
    }

    @Override
    public Gadget editGadget(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id, @RequestBody String json,
                             @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return gadgetService.edit(parseID(id), json);
    }

    @Override
    public Gadget deleteGadget(@PathVariable String brandID, @PathVariable String carID, @PathVariable String cID, @PathVariable String id,
                               @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return gadgetService.delete(parseID(id));
    }
}
