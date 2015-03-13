package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.Gadget;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 07.02.15.
 */
@Repository
public class GadgetDAO extends BaseDAO<Gadget> {

    public GadgetDAO(){
        super(Gadget.class);
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
