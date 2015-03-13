package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.Comfort;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 07.02.15.
 */
@Repository
public class ComfortDAO extends BaseDAO<Comfort> {

    public ComfortDAO(){
        super(Comfort.class);
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
