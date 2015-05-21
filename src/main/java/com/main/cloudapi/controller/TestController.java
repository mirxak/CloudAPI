package com.main.cloudapi.controller;

import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.entity.KASKO;
import com.main.cloudapi.service.BrandService;
import com.main.cloudapi.service.CarService;
import com.main.cloudapi.service.callback.CallBackService;
import com.main.cloudapi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
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

    @Autowired
    CallBackService callBackService;

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

    @RequestMapping(value = "/pkasko/fill", method = RequestMethod.POST)
    @ResponseBody
    public String fillPkasko(){
        String apiKey = callBackService.get("http://pkasko.ru/auth/api?login=skyflyerswan@yandex.ru&password=123456", null);
        if (StringUtils.isBlank(apiKey)){
            throw new ThrowFabric.BadRequestException("callback result is null");
        }

        KASKO.ApiKey ak = JsonUtils.getFromJson(apiKey, KASKO.ApiKey.class, true);

        String result = callBackService.get("http://pkasko.ru/calcservice/cars",
                Collections.singletonMap("X-Authorization", ak.api_key));
        if (StringUtils.isBlank(result)){
            throw new ThrowFabric.BadRequestException("callback result is null");
        }

        List<KASKO.PkaskoBrand> pkaskoBrands = JsonUtils.getList(result, KASKO.PkaskoBrand.class, true);
        for (KASKO.PkaskoBrand pkaskoBrand : pkaskoBrands){
            for (KASKO.PkaskoCar pkaskoCar : pkaskoBrand.models){
                String nameLike = pkaskoBrand.name + " " + pkaskoCar.name;
                nameLike = nameLike.replaceAll("'", "");
                if (StringUtils.isBlank(nameLike)){
                    System.err.println("!+++++++++++++++++++  error=" + JsonUtils.getJson(pkaskoBrand,true));
                    System.err.println("!+++++++++++++++++++  error=" + JsonUtils.getJson(pkaskoCar, true));
                    throw new RuntimeException();
                }

                List<Car> cars = carService.getDAO().getForPkasko(nameLike + "%");
                if (cars.isEmpty())continue;
                int i;
                int count = 0;
                for (i=0;i<cars.size();i++){
                    Car car = cars.get(i);
                    if (car.getName().equals(nameLike)){
                        car.setPkaskoBrandName(pkaskoBrand.name);
                        car.setPkaskoCarName(pkaskoCar.name);
                        carService.simpleUpdate(car);
                        count++;
//                        break;
                    }
                    else if ((StringUtils.isBlank(car.getPkaskoBrandName())) ||
                            (StringUtils.isBlank(car.getPkaskoCarName()))){
                        car.setPkaskoBrandName(pkaskoBrand.name);
                        car.setPkaskoCarName(pkaskoCar.name);
                        carService.simpleUpdate(car);
                        count++;
                    }
                }

                if (count == 0){
                    Car car = cars.get(0);
                    car.setPkaskoBrandName(pkaskoBrand.name);
                    car.setPkaskoCarName(pkaskoCar.name);
                    carService.simpleUpdate(car);
                }
            }
        }

        return "ok";
    }

}
