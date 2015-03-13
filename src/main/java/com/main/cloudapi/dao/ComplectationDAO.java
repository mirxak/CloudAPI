package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.Complectation;
import com.main.cloudapi.entity.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

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

    @Override
    public User getCurrentUser() {
        return null;
    }
}
