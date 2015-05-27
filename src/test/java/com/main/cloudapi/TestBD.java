package com.main.cloudapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.dao.ComplectationDAO;
import com.main.cloudapi.entity.*;
import com.main.cloudapi.service.CalculateService;
import com.main.cloudapi.service.CarService;
import com.main.cloudapi.service.BrandService;
import com.main.cloudapi.service.EmailSender;
import com.main.cloudapi.service.callback.CallBackService;
import com.main.cloudapi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by mirxak on 21.03.15.
 */
@ContextConfiguration({"classpath:mvc-dispatcher-servlet.xml", "classpath:hibernate.cfg.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class TestBD extends AbstractTransactionalTestNGSpringContextTests {

    private static final String BASE_URL = "http://intellect-drive.com/img/pic_auto/";

    @Autowired
    BrandService brandService;

    @Autowired
    CarService carService;

    @Autowired
    CalculateService calculateService;

    @Autowired
    EmailSender emailSender;

    @Autowired
    CallBackService callBackService;

    @Test(enabled = true)
//    @Rollback(value = false)
    public void addLinksToBD(){
        List<Brand> brands = brandService.getAllBrands();
        if ((brands == null) || (brands.isEmpty())){
            throw new ThrowFabric.BadRequestException("brands is null");
        }
        for (Brand brand : brands){
            String img_url = BASE_URL + brand.getName() + "/" + brand.getName() + ".png";
            brand.setImgUrl(img_url);
            try{
            brandService.simpleUpdate(brand);
            }
            catch (Exception e){
                System.out.println("!++++++++++++++++       brand=" + brand.getId());
                throw e;
            }

//            List<Car> cars = carService.getCarsOfBrand(brand.getId());
            Set<Car> cars = brand.getCars();
            if (cars.isEmpty())continue;
            for (Car car : cars){
                car.setImgUrl(BASE_URL + brand.getName() + "/" + car.getName().replaceAll(" ", "_") + ".jpeg");
                car.setImgUrl1(BASE_URL + brand.getName() + "/" + car.getName().replaceAll(" ", "_") + "_" + 1 + ".jpeg");
                car.setImgUrl2(BASE_URL + brand.getName() + "/" + car.getName().replaceAll(" ", "_") + "_" + 2 + ".jpeg");
                car.setImgUrl3(BASE_URL + brand.getName() + "/" + car.getName().replaceAll(" ", "_") + "_" + 3 + ".jpeg");
                car.setImgUrl4(BASE_URL + brand.getName() + "/" + car.getName().replaceAll(" ", "_") + "_" + 4 + ".jpeg");
                try{
                    carService.simpleUpdate(car);
                }
                catch (Exception e){
                    System.out.println("!+++++++++++++++++++        car=" + car.getId());
                    throw e;
                }
            }
        }

    }

    @Test(enabled = true)
    public void testFilter(){
        String calcJson = "{\"complectation_filter\":{\"brand_ids\":[211,213],\"gearbox\":[\"Автоматическая\",\"Механическая\",\"Робот\",\"Вариатор\"],\"gearing\":[\"Передний\",\"Задний\",\"Полный\"],\"complectation_price_min\":1,\"complectation_price_max\":10000000},\"fuel_price\":50,\"milage\":10000,\"is_credit\":0,\"kind_of_insurance\":1,\"driving_experience\":8,\"age\":28,\"expense_min\":10000,\"expense_max\":20000}";

        calculateService.calculateMain(calcJson);
//        System.out.println("!+++++++++++++      cmps.size=" + cmps.size());
    }

    @Test(enabled = true)
    public void test(){
        Mail mail = new Mail();
        mail.setSubject("test");
        mail.setMessage("test");
        mail.setEmail("mir-ehjnari@yandex.ru");
        try {
            emailSender.sendMail(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true)
    public void testSetPkaskoNames(){
        String apiKey = callBackService.get("http://pkasko.ru/auth/api?login=skyflyerswan@yandex.ru&password=123456", null);
        if (StringUtils.isBlank(apiKey)){
            throw new ThrowFabric.BadRequestException("callback result is null");
        }

        ApiKey ak = JsonUtils.getFromJson(apiKey, ApiKey.class, true);

        String result = callBackService.get("http://pkasko.ru/calcservice/cars",
                         Collections.singletonMap("X-Authorization", ak.api_key));
        if (StringUtils.isBlank(result)){
            throw new ThrowFabric.BadRequestException("callback result is null");
        }

        List<PkaskoBrand> pkaskoBrands = JsonUtils.getList(result, PkaskoBrand.class, true);
        for (PkaskoBrand pkaskoBrand : pkaskoBrands){
            for (PkaskoCar pkaskoCar : pkaskoBrand.models){
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
    }

    @Test(enabled = true)
    public void testCalculate(){
        String json = "{\"complectation_filter\":{\"brand_ids\":[211,213,210],\"gearbox\":[\"Автоматическая\",\"Робот\",\"Вариатор\",\"Механическая\"],\"gearing\":[\"Полный\",\"Задний\"],\"complectation_price_min\":100000,\"complectation_price_max\":10000000},\"user_complectations\":[],\"fuel_price\":35,\"milage\":15000,\"age\":25,\"driving_experience\":5,\"is_credit\":1,\"time_credit\":\"36\",\"first_payment\":\"500000\",\"ap\":\"50000\",\"kind_of_insurance\":1, \"percent\":20}";
        calculateService.calculateMain(json);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PkaskoBrand{
        public String name;
        public List<PkaskoCar> models;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PkaskoCar{
        public String name;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApiKey{
        public String api_key;
    }
}
