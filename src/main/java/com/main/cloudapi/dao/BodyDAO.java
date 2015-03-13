package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.Body;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 23.01.15.
 */
@Repository
public class BodyDAO extends BaseDAO<Body>{

    public BodyDAO(){
        super(Body.class);
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
