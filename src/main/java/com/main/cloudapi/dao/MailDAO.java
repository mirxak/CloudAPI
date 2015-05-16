package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.Mail;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 16.05.15.
 */
@Repository
public class MailDAO extends BaseDAO<Mail> {
    @Override
    public User getCurrentUser() {
        return null;
    }
}
