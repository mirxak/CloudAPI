package com.main.cloudapi.controller;

import com.main.cloudapi.api.UserControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.User;
import com.main.cloudapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@Controller
public class UserController extends BaseController implements UserControllerI {

    @Autowired
    UserService userService;

    @Override
    public User getUser(@PathVariable String id) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
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
    public User activate(@PathVariable String id, @RequestBody String json) {
        return userService.activate(parseID(id), json);
    }

    @Override
    public User editUser(@PathVariable String id, @RequestBody String json) {
        return null;
    }

    @Override
    public User deleteUser(@PathVariable String id) {
        return null;
    }
}
