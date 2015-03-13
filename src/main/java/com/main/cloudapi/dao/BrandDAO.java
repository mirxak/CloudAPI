package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.Brand;
import com.main.cloudapi.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 24.01.15.
 */
@Repository
public class BrandDAO extends BaseDAO<Brand> {

    public BrandDAO(){
        super(Brand.class);
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

}
