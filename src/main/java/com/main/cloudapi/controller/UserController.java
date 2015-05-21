package com.main.cloudapi.controller;

import com.main.cloudapi.api.UserControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.User;
import com.main.cloudapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@Controller
public class UserController extends BaseController implements UserControllerI {

    @Autowired
    UserService userService;

    @Override
    public User getUser(@PathVariable String id,
                        @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        return userService.getUser(parseID(id));
    }

    @Override
    public List<User> getUsers(@RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        return userService.getUsers();
    }

    @Override
    public User register(@RequestBody String json) {
        return userService.register(json);
    }

    @Override
    public User login(@RequestBody String json) {
        return userService.login(json);
    }

    @Override
    public User exit(@PathVariable String id,
                     @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        return userService.exit(parseID(id));
    }

    @Override
    public User activate(@PathVariable String id, @PathVariable String activateToken) {
        return userService.activate(parseID(id), activateToken);
    }

    @Override
    public User editUser(@PathVariable String id, @RequestBody String json,
                         @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        return userService.editUser(parseID(id), json);
    }

    @Override
    public User deleteUser(@PathVariable String id,@RequestParam(value = "access_token", defaultValue = "", required = false)String access_token
                           ) {
        userService.validateToken(access_token);
        return userService.deleteUser(parseID(id));
    }
}
