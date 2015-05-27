package com.main.cloudapi.controller;

import com.main.cloudapi.api.BrandControllerI;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.service.BrandService;
import com.main.cloudapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@Controller
public class BrandController extends BaseController implements BrandControllerI {

    @Autowired
    BrandService brandService;

    @Autowired
    UserService userService;

    @Override
    public Brand getBrand(@PathVariable String id) {
        return brandService.getById(parseID(id));
    }

    @Override
    public List<Brand> getBrands() {
        return brandService.getAllBrands();
    }

    @Override
    public Brand addBrand(@RequestBody String json,
                          @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return brandService.addBrand(json);
    }

    @Override
    public Brand editBrand(@PathVariable String id, @RequestBody String json,
                           @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return brandService.editBrand(parseID(id), json);
    }

    @Override
    public Brand deleteBrand(@PathVariable String id,
                             @RequestParam(value = "access_token", defaultValue = "", required = false)String access_token) {
        userService.validateToken(access_token);
        if (!userService.checkPermission(userService.getCurUser().getId())){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        return brandService.deleteBrand(parseID(id));
    }

}
