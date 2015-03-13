package com.main.cloudapi.api;

import com.main.cloudapi.entity.Brand;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mirxak on 24.01.15.
 */
@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"}, value = "/" + Brand.CATEGORY)
public interface BrandControllerI {

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Brand getBrand(@PathVariable String id);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Brand> getBrands();

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Brand addBrand(@RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Brand editBrand(@PathVariable String id, @RequestBody String json);

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Brand deleteBrand(@PathVariable String id);
}
