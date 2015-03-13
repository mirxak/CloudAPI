package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.EngineGearbox;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 07.02.15.
 */
@Repository
public class EngineDAO extends BaseDAO<EngineGearbox> {

    public EngineDAO(){
        super(EngineGearbox.class);
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
