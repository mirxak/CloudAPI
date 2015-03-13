package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.entity.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 01.02.15.
 */
@Repository
public class CarDAO extends BaseDAO<Car> {

    public CarDAO(){
        super(Car.class);
    }

    public Criteria createCriteria(Long brandID){
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("brandId", brandID));
        return criteria;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
