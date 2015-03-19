package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.Settings;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 20.03.15.
 */
@Repository
public class SettingsDAO extends BaseDAO<Settings>{

    public SettingsDAO(){
        super(Settings.class);
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
