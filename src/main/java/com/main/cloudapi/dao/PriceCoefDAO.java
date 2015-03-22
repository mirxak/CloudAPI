package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.PriceCoef;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 22.03.15.
 */
@Repository
public class PriceCoefDAO extends BaseDAO<PriceCoef> {

    public PriceCoefDAO(){
        super(PriceCoef.class);
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
