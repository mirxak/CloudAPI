package com.main.cloudapi.controller;

import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.service.BrandService;
import com.main.cloudapi.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

/**
 * Created by mirxak on 22.03.15.
 */
@Controller
public class TestController extends BaseController {

    private static final String BASE_URL = "http://intellect-drive.com/img/pic_auto/";

    @Autowired
    BrandService brandService;

    @Autowired
    CarService carService;

    @RequestMapping(value = "/add_image_to_bd", method = RequestMethod.POST)
    @ResponseBody
    public String addToBD(){
        List<Brand> brands = brandService.getAllBrands();
        if ((brands == null) || (brands.isEmpty())){
            throw new ThrowFabric.BadRequestException("brands is null");
        }
        for (Brand brand : brands){
            String img_url = BASE_URL + brand.getName().replaceAll(" ", "_") + "/" + brand.getName().replaceAll(" ", "_") + ".png";
            brand.setImgUrl(img_url);
            brandService.simpleUpdate(brand);

//            List<Car> cars = carService.getCarsOfBrand(brand.getId());
            Set<Car> cars = brand.getCars();
            if (cars.isEmpty())continue;
            for (Car car : cars){
                car.setImgUrl(BASE_URL + brand.getName().replaceAll(" ", "_") + "/" + car.getName().replaceAll(" ", "_") + ".jpeg");
                car.setImgUrl1(BASE_URL + brand.getName().replaceAll(" ", "_") + "/" + car.getName().replaceAll(" ", "_") + "_" + 1 + ".jpeg");
                car.setImgUrl2(BASE_URL + brand.getName().replaceAll(" ", "_") + "/" + car.getName().replaceAll(" ", "_") + "_" + 2 + ".jpeg");
                car.setImgUrl3(BASE_URL + brand.getName().replaceAll(" ", "_") + "/" + car.getName().replaceAll(" ", "_") + "_" + 3 + ".jpeg");
                car.setImgUrl4(BASE_URL + brand.getName().replaceAll(" ", "_") + "/" + car.getName().replaceAll(" ", "_") + "_" + 4 + ".jpeg");
                carService.simpleUpdate(car);
            }
        }
        return "ok";
    }
}
