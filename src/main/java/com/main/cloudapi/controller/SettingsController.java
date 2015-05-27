package com.main.cloudapi.controller;

import com.main.cloudapi.api.SettingsControllerI;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Settings;
import com.main.cloudapi.service.SettingsService;
import com.main.cloudapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by mirxak on 20.03.15.
 */
@Controller
public class SettingsController extends BaseController implements SettingsControllerI{

    @Autowired
    SettingsService settingsService;

    @Autowired
    UserService userService;

    @Override
    public Settings getSetting(@PathVariable String id) {
        return settingsService.getById(parseID(id));
    }

    @Override
    public List<Settings> getSettings() {
        return settingsService.getAll();
    }

    @Override
    public Settings addSetting(@RequestBody String json,
                               @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return settingsService.addSetting(json);
    }

    @Override
    public Settings editSetting(@PathVariable String id, @RequestBody String json,
                                @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return settingsService.editSetting(parseID(id), json);
    }

    @Override
    public Settings deleteSetting(@PathVariable String id,
                                  @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return settingsService.deleteSetting(parseID(id));
    }
}
