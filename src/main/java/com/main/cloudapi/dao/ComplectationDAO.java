package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mirxak on 01.02.15.
 */
@Repository
public class ComplectationDAO extends BaseDAO<Complectation> {

    public ComplectationDAO(){
        super(Complectation.class);
    }

    public Criteria createCriteria(Long carID){
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("carId", carID));
        return criteria;
    }

    private String getSQLForGear(List<String> gearbox, List<String> gearing){
        String sqlQ = "(";
        for (int i=0; i<gearbox.size(); i++){
            sqlQ += "(lower(eg.gearbox) like '" + gearbox.get(i).toLowerCase() + "%')";
            if (i != gearbox.size()-1){
                sqlQ += " or ";
            }
        }
        sqlQ += ") and (";

        for (int i=0; i<gearing.size(); i++){
            sqlQ += "(lower(eg.gearing)='" + gearing.get(i).toLowerCase() + "')";
            if (i != gearing.size()-1){
                sqlQ += " or ";
            }
        }
        sqlQ += ")";

        return sqlQ;
    }

    public List<Complectation> getForCalculate(CalcFilter.ComplectationFilter calcFilter){
        Session session = getSession();
        String fields = "cmp.id as id, cmp.name as name, cmp.engine_id as engine_id, cmp.body_id as body_id, cmp.safety_id as safety_id, " +
                        "cmp.comfort_id as comfort_id, cmp.gadgets_id as gadgets_id, cmp.car_id car_id, cmp.price as price, " +
                        "cmp.created_by as created_by, cmp.created_date as created_date, cmp.is_deleted as is_deleted, cmp.deleted_by as deleted_by, " +
                        "cmp.deleted_date as deleted_date, cmp.last_modified_date as last_modified_date, cmp.last_modified_user as last_modified_user, cmp.co2 as co2";
        String query = "select " + fields + " from ";
        query +=       Complectation.TABLE + " as cmp, " + EngineGearbox.TABLE +
                       " as eg, " + Body.TABLE + " as b, " + Car.TABLE + " as c where (coalesce(cmp.is_deleted,0)<>1) " +
                       "and (cmp.engine_id=eg.id) and (coalesce(eg.is_deleted)<>1) and " +
                       "(cmp.body_id=b.id) and (coalesce(b.is_deleted)<>1) and " +
                       "(cmp.car_id=c.id) and (coalesce(c.is_deleted)<>1) and (c.brand_id in (:brandIds)) "+
                       "and ((eg.power >= :pwrMin) and (eg.power <= :pwrMax)) and " + getSQLForGear(calcFilter.getGearbox(), calcFilter.getGearing()) +
                       " and ((eg.acceleration_to_hund >= :accMin) and (eg.acceleration_to_hund <= :accMax)) " +
                       "and ((eg.full_speed >= :fsMin) and (eg.full_speed <= :fsMax)) " +
                       "and ((eg.fuel_consum_city >= :fccMin) and (eg.fuel_consum_city <= :fccMax)) " +
                       "and ((eg.fuel_consum_track >= :fctMin) and (eg.fuel_consum_track <= :fctMax)) " +
                       "and ((eg.fuel_consum_mixed >= :fcmMin) and (eg.fuel_consum_mixed <= :fcmMax)) " +
                       "and ((b.clearance >= :clMin) and (b.clearance <= :clMax)) " +
                       "and ((b.luggage_amount >= :laMin) and (b.luggage_amount <= :laMax)) " +
                       "and ((b.fuel_capacity >= :fcMin) and (b.fuel_capacity <= :fcMax)) " +
                       "and ((cmp.price >= :priceMin) and (cmp.price <= :priceMax))";
//                       "and ((cmp.co2 >= :co2Min) and (cmp.co2 <= :co2Max))";

        Query sqlQuery = session.createSQLQuery(query).addEntity(Complectation.class);
        sqlQuery.setParameterList("brandIds", calcFilter.getBrand_ids());
        sqlQuery.setInteger("pwrMin", calcFilter.getPowerMin());
        sqlQuery.setInteger("pwrMax", calcFilter.getPowerMax());
        sqlQuery.setFloat("accMin", calcFilter.getAccelerationToHundMin());
        sqlQuery.setFloat("accMax", calcFilter.getAccelerationToHundMax());
        sqlQuery.setInteger("fsMin", calcFilter.getFullSpeedMin());
        sqlQuery.setInteger("fsMax", calcFilter.getFullSpeedMax());
        sqlQuery.setInteger("fccMin", calcFilter.getFuelConsumCityMin());
        sqlQuery.setInteger("fccMax", calcFilter.getFuelConsumCityMax());
        sqlQuery.setInteger("fctMin", calcFilter.getFuelConsumTrackMin());
        sqlQuery.setInteger("fctMax", calcFilter.getFuelConsumTrackMax());
        sqlQuery.setInteger("fcmMin", calcFilter.getFuelConsumMixedMin());
        sqlQuery.setInteger("fcmMax", calcFilter.getFuelConsumMixedMax());
        sqlQuery.setInteger("clMin", calcFilter.getClearanceMin());
        sqlQuery.setInteger("clMax", calcFilter.getClearanceMax());
        sqlQuery.setInteger("laMin", calcFilter.getLuggageAmountMin());
        sqlQuery.setInteger("laMax", calcFilter.getLuggageAmountMax());
        sqlQuery.setInteger("fcMin", calcFilter.getFuelCapacityMin());
        sqlQuery.setInteger("fcMax", calcFilter.getFuelCapacityMax());
        sqlQuery.setBigInteger("priceMin", BigInteger.valueOf(calcFilter.getComplectationPriceMin()));
        sqlQuery.setBigInteger("priceMax", BigInteger.valueOf(calcFilter.getComplectationPriceMax()));
//        sqlQuery.setInteger("co2Min", calcFilter.getCo2Min());
//        sqlQuery.setInteger("co2Max", calcFilter.getCo2Max());

        List<Complectation> complectations = sqlQuery.list();
        if ((complectations == null) || (complectations.isEmpty())){
            return Collections.emptyList();
        }

        List<Complectation> result = new ArrayList<>();
        for (Complectation cmp : complectations){
            String engine = cmp.getEngineGearbox().getEngine();
            engine = engine.replaceAll("[^\\d]", "");
            Integer volume = Integer.valueOf(engine);
            if ((volume >= calcFilter.getVolumeMin()) && (volume <= calcFilter.getVolumeMax())){
                result.add(cmp);
            }
        }

        return result;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
