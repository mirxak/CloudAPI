package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.Safety;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 07.02.15.
 */
@Repository
public class SafetyDAO extends BaseDAO<Safety> {

    public SafetyDAO(){
        super(Safety.class);
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
