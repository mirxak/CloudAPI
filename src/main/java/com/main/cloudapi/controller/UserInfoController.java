package com.main.cloudapi.controller;

import com.main.cloudapi.api.UserInfoControllerI;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@Controller
public class UserInfoController implements UserInfoControllerI {
    @Override
    public User getUserInfo(@PathVariable String userID, @PathVariable String id) {
        return null;
    }

    @Override
    public List<User> getUserInfos(@PathVariable String userID) {
        return null;
    }

    @Override
    public User addUserInfo(@PathVariable String userID, @RequestBody String json) {
        return null;
    }

    @Override
    public User editUserInfo(@PathVariable String userID, @PathVariable String id, @RequestBody String json) {
        return null;
    }

    @Override
    public User deleteUserInfo(@PathVariable String userID, @PathVariable String id) {
        return null;
    }
}
