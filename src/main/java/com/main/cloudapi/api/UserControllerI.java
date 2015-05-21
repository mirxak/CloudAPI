package com.main.cloudapi.api;

import com.main.cloudapi.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"}, value = "/" + User.CATEGORY)
public interface UserControllerI {

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable String id,
                        @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers(@RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

    @RequestMapping(value="register", method = RequestMethod.POST)
    @ResponseBody
    public User register(@RequestBody String json);

    @RequestMapping(value="login", method = RequestMethod.POST)
    @ResponseBody
    public User login(@RequestBody String json);

    @RequestMapping(value = "{id}/exit", method = RequestMethod.POST)
    @ResponseBody
    public User exit(@PathVariable String id,
                     @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

    @RequestMapping(value = "{id}/activate/{activateToken}", method = RequestMethod.GET)
    @ResponseBody
    public User activate(@PathVariable String id, @PathVariable String activateToken);

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    @ResponseBody
    public User editUser(@PathVariable String id, @RequestBody String json,
                         @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public User deleteUser(@PathVariable String id,
                           @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token);

}
