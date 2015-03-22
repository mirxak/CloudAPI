package com.main.cloudapi.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.dao.CarServiceDAO;
import com.main.cloudapi.dao.ComplectationDAO;
import com.main.cloudapi.dao.SettingsDAO;
import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.*;
import com.main.cloudapi.utils.ContextHolder;
import com.main.cloudapi.utils.JsonUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mirxak on 15.03.15.
 */
@Service
public class CalculateService {

    private static final Integer CALC_SERVICE_YEAR = 3;

    @Autowired
    BrandService brandService;

    @Autowired
    CarServiceDAO carServiceDAO;

    @Autowired
    SettingsDAO settingsDAO;

    @Autowired
    ComplectationDAO complectationDAO;

    private Integer getLengthOfBody(Body body){
        String length = body.getDimensions();
        length = length.substring(0, length.indexOf(" "));
        return Integer.valueOf(length);
    }

    private Float getLengthCoef(ServiceParams serviceParams){
        Criteria settingCrit = settingsDAO.createCriteria();
        settingCrit.add(Restrictions.eq("name", "length_coef"));
        List<Settings> settingsList = settingsDAO.getAll(BaseDAO.STD_OFFSET, BaseDAO.STD_LIMIT, settingCrit);
        if ((settingsList == null) || (settingsList.isEmpty())){
            throw new ThrowFabric.BadRequestException("error getting settings");
        }
        List<CoefLength> coefLengths = JsonUtils.getList(settingsList.get(0).getSetting(), CoefLength.class, true);
        Complectation complectation = complectationDAO.getById(serviceParams.complectation_id, true);
        Integer carLength = getLengthOfBody(complectation.getBody());
        Float result = null;
        for (CoefLength coefLength : coefLengths){
            if ((carLength >= coefLength.min_length) && (carLength <= coefLength.max_length)){
                result = coefLength.coef;
            }
        }

        if (result == null){
            throw new ThrowFabric.BadRequestException("settings.length is incorrect. car lenght is " + carLength);
        }

        return result;
    }

    // Расчёт ТО на 3 года
    public Float calcTOService(String json){
        ServiceParams serviceParams = JsonUtils.getFromJson(json, ServiceParams.class, true);
        Brand brand = brandService.getById(serviceParams.brandId);
        List<com.main.cloudapi.entity.CarService> carServices;
        Criteria criteria = carServiceDAO.createCriteria();
        Integer milage_partition = ContextHolder.getData().getMilage_partition();
        Float lengthCoef = getLengthCoef(serviceParams);

        Float price = new Float(0f);
        if (serviceParams.milage <= milage_partition){
            List<Integer> servTimes = new ArrayList<>();
            for (int i=1;i<=CALC_SERVICE_YEAR; i++){
                servTimes.add(i);
            }
            // get by serviceTime for 3 years
            criteria.add(Restrictions.eq("brandId", serviceParams.brandId))
                    .add(Restrictions.in("serviceTime", servTimes));
            carServices = carServiceDAO.getAll(BaseDAO.STD_OFFSET, BaseDAO.STD_LIMIT, criteria);
            if ((carServices == null) || (carServices.isEmpty())){
                throw new ThrowFabric.BadRequestException("There are no services for brand=" + brand.getName());
            }

            for (com.main.cloudapi.entity.CarService carService : carServices){
                price += carService.getPriceMin() + (carService.getPriceMax() - carService.getPriceMin())*brand.getServiceCoef()*lengthCoef;
            }
        }
        else{
            Integer mil = serviceParams.milage * 3;
            criteria.add(Restrictions.eq("brandId", serviceParams.brandId))
                    .add(Restrictions.le("milage", mil));

            carServices = carServiceDAO.getAll(BaseDAO.STD_OFFSET, BaseDAO.STD_LIMIT, criteria);
            for (int i=0; i<carServices.size(); i++){
                price += carServices.get(i).getPriceMin() + (carServices.get(i).getPriceMax() - carServices.get(i).getPriceMin())*brand.getServiceCoef()*lengthCoef;
                if (i == carServices.size()-1){
                    Integer cur_m = carServices.get(i).getMilage();
                    while(cur_m + milage_partition <= serviceParams.milage){
                        price += carServices.get(i).getPriceMin() + (carServices.get(i).getPriceMax() - carServices.get(i).getPriceMin())*brand.getServiceCoef()*lengthCoef;
                        cur_m += milage_partition;
                    }
                }
            }
        }

        return price;
    }

    public Float calculateOSAGO(){

        return null;
    }

    private List<Complectation> getFilterComplectations(CalcFilter calcFilter){

        return Collections.emptyList();
    }

    public String calculateMain(String json){
        CalcFilter calcFilter = JsonUtils.getFromJson(json, CalcFilter.class, true);


        return "ok";
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ServiceParams{
        public Integer milage; // ежегодный пробег клиента
        public Long brandId; // id марки
        public Long complectation_id;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CoefLength{
        public Integer min_length;
        public Integer max_length;
        public Float coef;
    }
}
