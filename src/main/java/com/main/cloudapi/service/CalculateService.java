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

import java.lang.reflect.Field;
import java.util.*;

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
    public Float calcTOService(ServiceParams serviceParams){
        Brand brand = brandService.getById(serviceParams.brandId);
        List<com.main.cloudapi.entity.CarService> carServices;
        Criteria criteria = carServiceDAO.createCriteria();
        Integer milage_partition = ContextHolder.getData().getMilage_partition();
//        Float lengthCoef = getLengthCoef(serviceParams);
        Float lengthCoef = 1.0f; // временно

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

    private Float getKVS(Integer de, Integer age){
        if ((age <= 22) && (de <= 3)){
            return 1.8f;
        }
        else if ((age <= 22) && (de > 3)){
            return 1.6f;
        }
        else if ((age > 22) && (de <= 3)){
            return 1.7f;
        }
        else if ((age > 22) && (de > 3)){
            return 1f;
        }

        return 0f;
    }

    private Float getPowerCoef(Integer power){
        if (power <= 50){
            return 0.6f;
        }
        else if ((power >= 51) && (power <= 70)){
            return 1.0f;
        }
        else if ((power >= 71) && (power <= 100)){
            return 1.1f;
        }
        else if ((power >= 101) && (power <= 120)){
            return 1.2f;
        }
        else if ((power >= 121) && (power <= 150)){
            return 1.4f;
        }

        return 1.6f;
    }

    // Базовый тариф для физ. лиц. Легковые автомобили(В) - 1980
    // Коэффициент территории для Санкт-Петербурга - 1.8
    // Количество водителей - 1
    public Float calculateOSAGO(CalcFilter calcFilter, Integer power){
        return (1980f)*(1.8f)*getKVS(calcFilter.getDriving_experience(), calcFilter.getAge())*
               getPowerCoef(power);
    }

    //Расчет затрат на топливо в месяц
    private Float calcFuelCost(Integer fuelConsumMixed, Integer milage, Float fuelPrice){
        return (milage/1200)*fuelConsumMixed*fuelPrice;
    }

    //Расчет кредитования
    private CreditResult calcCredit(CalcFilter calcFilter, Long cmp_price){
        Double sk = new Double(cmp_price - calcFilter.getFirst_payment());
        Float percent;
        if (calcFilter.getPercent() != null){
            percent = calcFilter.getPercent();
        }
        else{
            Brand brand = brandService.getById(calcFilter.getBrand_id());
            percent = brand.getCreditPercent();
        }
        Double pc = new Double(percent/12);
        pc = pc / 100;
        Double ap = sk*(pc + (pc/(Math.pow(1+pc, calcFilter.getTime_credit()-1) )-1));
        CreditResult creditResult = new CreditResult();
        creditResult.ap = ap;
        creditResult.overpayment = ap*calcFilter.getTime_credit() - sk;
        Double np = new Double(0);
        for (int i=0;i<calcFilter.getTime_credit();i++){
            np += sk*pc;
            sk -= ap;
        }
        creditResult.percent_per_year = np;
        return creditResult;
    }

    private Float getLengthPercent(Body body){
        Integer length = getLengthOfBody(body);

        Float percent;
        if (length < 3600){
            percent = 0.5f;
        }
        else if ((length >= 3600) && (length < 3900)){
            percent = 0.4f;
        }
        else if ((length >= 3900) && (length < 4400)){
            percent = 0.3f;
        }
        else if ((length >= 4400) && (length < 4700)){
            percent = 0.28f;
        }
        else{
            percent = 0.26f;
        }

        return percent/100;
    }

    private Float getDurabilityCoef(Integer milage){
        Float percent;
        if (milage < 2000){
            percent = 1.63f;
        }
        else if ((milage >= 2000) && (milage < 5000)){
            percent = 1.49f;
        }
        else if ((milage >= 5000) && (milage < 10000)){
            percent = 1.27f;
        }
        else if ((milage >= 10000) && (milage < 15000)){
            percent = 1.0f;
        }
        else if ((milage >= 15000) && (milage < 20000)){
            percent = 0.89f;
        }
        else if ((milage >= 20000) && (milage < 30000)){
            percent = 0.82f;
        }
        else if ((milage >= 30000) && (milage < 40000)){
            percent = 0.77f;
        }
        else if ((milage >= 40000) && (milage < 60000)){
            percent = 0.7f;
        }
        else if ((milage >= 60000) && (milage < 100000)){
            percent = 0.64f;
        }
        else{
            percent = 0f;
        }

        return percent/100;
    }

    // Расчет амортизации авто
    private List<Float> calcAmortization(Complectation complectation, Integer milage){
        Float i1 = getLengthPercent(complectation.getBody());
        Float i2 = getDurabilityCoef(milage);
        Long price = complectation.getPrice();

        List<Float> result = new ArrayList<>();

        Integer pf = milage;
        Integer df = 1;
        while (pf <= milage * 3){
            Float itr = (i1*pf + i2*df)*1.05f*1.04f*1.1f;
            result.add(price - (price - price*itr)*0.8f);
            pf += milage;
            df++;
        }

        return result;
    }

    public List<CalculateResult> calculateMain(String json){
        CalcFilter calcFilter = JsonUtils.getFromJson(json, CalcFilter.class, true);
        calcFilter = validateFilter(calcFilter);
        List<Complectation> complectations = complectationDAO.getForCalculate(calcFilter);
        if (complectations.isEmpty()){
            throw new ThrowFabric.BadRequestException("There are not complectations for this parameters");
        }

        if (!calcFilter.getUserComplectations().isEmpty()){
            Set<Long> curIds = new HashSet<>();
            for (Complectation c : complectations){
                curIds.add(c.getId());
            }

            List<Long> cIds = new ArrayList<>();
            for (Complectation cmp : calcFilter.getUserComplectations()){
                if ((cmp.getId() != null) && (!curIds.contains(cmp.getId()))){
                    cIds.add(cmp.getId());
                }
            }

            Criteria criteria = complectationDAO.createCriteria();
            criteria.add(Restrictions.in("id", cIds));
            complectations.addAll(complectationDAO.getAll(BaseDAO.STD_OFFSET, BaseDAO.STD_LIMIT, criteria));
        }

        List<CalculateResult> calculateResults = new ArrayList<>();
        for (Complectation complectation : complectations){
            ServiceParams serviceParams = new ServiceParams();
            serviceParams.milage = calcFilter.getMilage();
            serviceParams.brandId = calcFilter.getBrand_id();
            serviceParams.complectation_id = complectation.getId();
            CalculateResult calculateResult = new CalculateResult();
            calculateResult.complectation = complectation;
            calculateResult.TO = calcTOService(serviceParams);
            calculateResult.month_fuel_cost = calcFuelCost(complectation.getEngineGearbox().getFuelConsumMixed(),
                                                           calcFilter.getMilage(), calcFilter.getFuel_price());
            if (calcFilter.getIs_credit().equals(1)){
                calculateResult.creditResult = calcCredit(calcFilter, complectation.getPrice());
            }
            calculateResult.OSAGO_price = calculateOSAGO(calcFilter, complectation.getEngineGearbox().getPower());
            calculateResult.amortization = calcAmortization(complectation, calcFilter.getMilage());
            // расчет в месяц
            Float expense = calculateResult.TO/36 + calculateResult.month_fuel_cost;
            if (calcFilter.getIs_credit().equals(1)){
                expense += new Float(calculateResult.creditResult.ap);
            }
            expense += calculateResult.OSAGO_price/12;
            expense += calculateResult.amortization.get(0)/12;
            calculateResult.expense = expense;
            calculateResults.add(calculateResult);
        }

        List<CalculateResult> mainResult = new ArrayList<>();
        for (CalculateResult calcRes : calculateResults){
            if ((calcRes.expense >= calcFilter.getExpense_min()) &&
                (calcRes.expense <= calcFilter.getExpense_max())){
                mainResult.add(calcRes);
            }
        }

        return mainResult;
    }

    // нулл поля сделать в мин или макс по типу
    private CalcFilter validateFilter(CalcFilter calcFilter){
        Field[] fields = calcFilter.getClass().getFields();
        try{
            for (Field field : fields){
                if (field.getType() == Integer.class){
                    if (field.getName().indexOf("Min") >= 0){
                        field.set(calcFilter, Integer.MIN_VALUE);
                    }
                    else if (field.getName().indexOf("Max") >= 0){
                        field.set(calcFilter, Integer.MAX_VALUE);
                    }
                }
                else if (field.getType() == Float.class){
                    if (field.getName().indexOf("Min") >= 0){
                        field.set(calcFilter, Float.MIN_VALUE);
                    }
                    else if (field.getName().indexOf("Max") >= 0){
                        field.set(calcFilter, Float.MAX_VALUE);
                    }
                }
                else if (field.getType() == Long.class){
                    if (field.getName().indexOf("Min") >= 0){
                        field.set(calcFilter, Long.MIN_VALUE);
                    }
                    else if (field.getName().indexOf("Max") >= 0){
                        field.set(calcFilter, Long.MAX_VALUE);
                    }
                }
            }
        }
        catch (Exception e){
            throw new ThrowFabric.BadRequestException("There is some error in filling calcFilter");
        }
        return calcFilter;
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

    public static class CalculateResult{
        public Complectation complectation;
        public Float TO;
        public Float month_fuel_cost;
        public CreditResult creditResult;
        public Float OSAGO_price; // на 1 год
        public List<Float> amortization;
        public Float expense;
    }

    public static class CreditResult{
        public Double ap; // ежемесячный платеж по кредиту
        public Double overpayment;
        public Double percent_per_year;
    }
}
