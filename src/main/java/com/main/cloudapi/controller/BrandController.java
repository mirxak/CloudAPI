package com.main.cloudapi.controller;

import com.main.cloudapi.api.BrandControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@Controller
public class BrandController extends BaseController implements BrandControllerI {

    @Autowired
    BrandService brandService;

    @Override
    public Brand getBrand(@PathVariable String id) {
        return brandService.getById(parseID(id));
    }

    @Override
    public List<Brand> getBrands() {
        return brandService.getAllBrands();
    }

    @Override
    public Brand addBrand(@RequestBody String json) {
        return brandService.addBrand(json);
    }

    @Override
    public Brand editBrand(@PathVariable String id, @RequestBody String json) {
        return brandService.editBrand(parseID(id), json);
    }

    @Override
    public Brand deleteBrand(@PathVariable String id) {
        return brandService.deleteBrand(parseID(id));
    }

}
