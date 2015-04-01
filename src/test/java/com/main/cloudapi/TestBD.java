package com.main.cloudapi;

import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.dao.ComplectationDAO;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.CalcFilter;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.entity.Complectation;
import com.main.cloudapi.service.CarService;
import com.main.cloudapi.service.BrandService;
import com.main.cloudapi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

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
    ComplectationDAO complectationDAO;

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
        CalcFilter calcFilter = new CalcFilter();
        calcFilter.setBrand_id(208L);
        calcFilter.setAccelerationToHundMin(1f);
        calcFilter.setAccelerationToHundMax(15f);
        calcFilter.setClearanceMin(100);
        calcFilter.setClearanceMax(250);
        calcFilter.setFuelCapacityMin(0);
        calcFilter.setFuelCapacityMax(500);
        calcFilter.setFuelConsumCityMin(0);
        calcFilter.setFuelConsumCityMax(30);
        calcFilter.setFuelConsumTrackMin(0);
        calcFilter.setFuelConsumTrackMax(50);
        calcFilter.setFuelConsumMixedMin(0);
        calcFilter.setFuelConsumMixedMax(50);
        calcFilter.setLuggageAmountMin(0);
        calcFilter.setLuggageAmountMax(1000);
        calcFilter.setComplectationPriceMin(0l);
        calcFilter.setComplectationPriceMax(2000000l);
        calcFilter.setFullSpeedMin(0);
        calcFilter.setFullSpeedMax(500);
        List<String> gearbox = new ArrayList<>();
        gearbox.add("автоматическая");
        gearbox.add("робот");
        calcFilter.setGearbox(gearbox);
        List<String> gearing = new ArrayList<>();
        gearing.add("передний");
        gearing.add("полный");
        calcFilter.setGearing(gearing);
        calcFilter.setVolumeMin(0);
        calcFilter.setVolumeMax(5000);
        calcFilter.setPowerMin(0);
        calcFilter.setPowerMax(1000);

        List<Complectation> cmps = complectationDAO.getForCalculate(calcFilter);
        System.out.println("!+++++++++++++      cmps.size=" + cmps.size());
    }

}
