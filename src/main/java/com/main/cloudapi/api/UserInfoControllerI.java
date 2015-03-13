package com.main.cloudapi.api;

import com.main.cloudapi.entity.User;
import com.main.cloudapi.entity.UserInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"},
                value = "/" + User.CATEGORY + "/{userID}/" + UserInfo.CATEGORY)
public interface UserInfoControllerI {

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUserInfo(@PathVariable String userID, @PathVariable String id);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUserInfos(@PathVariable String userID);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public User addUserInfo(@PathVariable String userID, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    @ResponseBody
    public User editUserInfo(@PathVariable String userID, @PathVariable String id, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public User deleteUserInfo(@PathVariable String userID, @PathVariable String id);

}
