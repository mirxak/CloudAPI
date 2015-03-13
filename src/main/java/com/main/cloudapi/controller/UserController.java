package com.main.cloudapi.controller;

import com.main.cloudapi.api.UserControllerI;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@Controller
public class UserController implements UserControllerI {
    @Override
    public User getUser(@PathVariable String id) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User addUser(@RequestBody String json) {
        return null;
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
