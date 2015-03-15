package com.main.cloudapi.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.dao.CarServiceDAO;
import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.CarService;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.utils.JsonUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Float calcService(String json){
        ServiceParams serviceParams = JsonUtils.getFromJson(json, ServiceParams.class, true);
        Brand brand = brandService.getById(serviceParams.brandId);
        List<CarService> carServices;
        Criteria criteria = carServiceDAO.createCriteria();
        if (serviceParams.milage < serviceParams.milage_partition){
            List<Integer> servTimes = new ArrayList<>();
            for (int i=1;i<=CALC_SERVICE_YEAR; i++){
                servTimes.add(i);
            }
            // get by serviceTime for 3 years
            criteria.add(Restrictions.eq("brandId", serviceParams.brandId))
                    .add(Restrictions.in("serviceTime", servTimes));
        }
        else{
            // нужно учитывать слишком большой пробег, т.е
            // последнюю услугу по пробегу умножать
            Integer mil = serviceParams.milage * 3;
            criteria.add(Restrictions.eq("brandId", serviceParams.brandId))
                    .add(Restrictions.le("milage", mil));
        }
        carServices = carServiceDAO.getAll(BaseDAO.STD_OFFSET, BaseDAO.STD_LIMIT, criteria);
        if ((carServices == null) || (carServices.isEmpty())){
            throw new ThrowFabric.BadRequestException("There are no services for brand=" + brand.getName());
        }

        Float price = new Float(0f);
        for (CarService carService : carServices){
            price += carService.getPriceMin() + (carService.getPriceMax() - carService.getPriceMin())*brand.getServiceCoef();
        }

        return price;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ServiceParams{
        public Integer milage; // ежегодный пробег клиента
        public Long brandId; // id марки
        public Integer milage_partition; // разбиение по пробегу: 15, 30, 45 ...
    }
}
