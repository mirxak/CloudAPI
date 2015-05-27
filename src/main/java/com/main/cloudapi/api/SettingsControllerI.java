package com.main.cloudapi.api;

/**
 * Created by mirxak on 20.03.15.
 */

import com.main.cloudapi.entity.Settings;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"}, value = Settings.CATEGORY)
public interface SettingsControllerI {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Settings getSetting(@PathVariable String id);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Settings> getSettings();

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Settings addSetting(@RequestBody String json,
                               @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Settings editSetting(@PathVariable String id, @RequestBody String json,
                                @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Settings deleteSetting(@PathVariable String id,
                                  @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);
}
