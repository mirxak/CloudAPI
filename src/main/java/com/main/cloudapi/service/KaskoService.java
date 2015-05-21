package com.main.cloudapi.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.entity.CalcFilter;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.entity.KASKO;
import com.main.cloudapi.service.callback.CallBackService;
import com.main.cloudapi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by mirxak on 22.05.15.
 */
@Service
public class KaskoService {

    @Autowired
    CallBackService callBackService;

    private String getApiKey(){
        String apiKey = callBackService.get("http://pkasko.ru/auth/api?login=skyflyerswan@yandex.ru&password=123456", null);
        if (StringUtils.isBlank(apiKey)){
            throw new ThrowFabric.BadRequestException("callback result is null");
        }

        KASKO.ApiKey ak = JsonUtils.getFromJson(apiKey, KASKO.ApiKey.class, true);
        return ak.api_key;
    }

    public List<KASKO.KaskoResult> calculateKASKO(CalcFilter calcFilter, Car car, Integer power,
                                                  Long price, Integer age, Integer drivingExp){
        String apiKey = getApiKey();

        KASKO.PkaskoCalcRequest requestBody = new KASKO.PkaskoCalcRequest();
        requestBody.make = car.getPkaskoBrandName();
        requestBody.model = car.getPkaskoCarName();
        requestBody.power = power.toString();
        requestBody.price = price;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        requestBody.year = String.valueOf(calendar.get(Calendar.YEAR));

        KASKO.PkaskoDriver driver = new KASKO.PkaskoDriver();
        driver.sex = "m";
        driver.age = age.toString();
        driver.marriage = true;
        driver.experience = drivingExp.toString();

        KASKO.PkaskoExtended extended = new KASKO.PkaskoExtended();
        extended.carNew = "Да";

        requestBody.drivers = Collections.singletonList(driver);
        requestBody.extended = extended;

//        Collections.singletonMap("X-Authorization", ak.api_key)

        String result = callBackService.post("http://pkasko.ru/kasko/calc?api=1",
                                             JsonUtils.getJson(requestBody, true),
                                             Collections.singletonMap("X-Authorization",apiKey));



        return null;
    }


}
