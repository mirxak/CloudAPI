package com.main.cloudapi.controller;

import com.main.cloudapi.api.SettingsControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Settings;
import com.main.cloudapi.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by mirxak on 20.03.15.
 */
@Controller
public class SettingsController extends BaseController implements SettingsControllerI{

    @Autowired
    SettingsService settingsService;

    @Override
    public Settings getSetting(@PathVariable String id) {
        return settingsService.getById(parseID(id));
    }

    @Override
    public List<Settings> getSettings() {
        return settingsService.getAll();
    }

    @Override
    public Settings addSetting(@RequestBody String json) {
        return settingsService.addSetting(json);
    }

    @Override
    public Settings editSetting(@PathVariable String id, @RequestBody String json) {
        return settingsService.editSetting(parseID(id), json);
    }

    @Override
    public Settings deleteSetting(@PathVariable String id) {
        return settingsService.deleteSetting(parseID(id));
    }
}
