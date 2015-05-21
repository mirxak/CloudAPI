package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.Car;
import com.main.cloudapi.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

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

    public List<Car> getForPkasko(String nameLike){
        Session session = getSession();

        Query query = session.createQuery("from Car where name like '" + nameLike +  "'");

        List<Car> result = (List<Car>)query.list();
        if ((result == null) || (result.isEmpty())){
            return Collections.emptyList();
        }

        return result;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
