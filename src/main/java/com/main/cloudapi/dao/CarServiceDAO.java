package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.CarService;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 15.03.15.
 */
@Repository
public class CarServiceDAO extends BaseDAO<CarService> {

    public CarServiceDAO(){
        super(CarService.class);
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
