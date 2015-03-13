package com.main.cloudapi.service;

import com.main.cloudapi.dao.NewsDAO;
import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.News;
import com.main.cloudapi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mirxak on 04.03.15.
 */
@Service
public class NewsService {

    @Autowired
    NewsDAO newsDAO;

    public News getById(Long id){
        return newsDAO.getById(id, true);
    }

    public List<News> getAllNews(){
        return newsDAO.getAll(BaseDAO.STD_OFFSET, BaseDAO.STD_LIMIT);
    }

    @Transactional
    public News addNews(String json){
        News news = JsonUtils.getFromJson(json, News.class, true);
        newsDAO.add(news);
        return news;
    }

    @Transactional
    public News editNews(Long id, String json){
        News news = getById(id);
        String title = news.getTitle();
        JsonUtils.update(json, news, true);
        return newsDAO.update(news);
    }

    @Transactional
    public News deleteNews(Long id){
        News news = getById(id);
        newsDAO.delete(news);
        return news;
    }

}
