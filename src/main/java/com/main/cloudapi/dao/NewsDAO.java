package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.News;
import com.main.cloudapi.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mirxak on 04.03.15.
 */
@Repository
public class NewsDAO extends BaseDAO<News> {

    public NewsDAO(){
        super(News.class);
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
