package com.main.cloudapi.controller;

import com.main.cloudapi.api.NewsControllerI;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.News;
import com.main.cloudapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by mirxak on 04.03.15.
 */
@Controller
public class NewsController extends BaseController implements NewsControllerI {

    @Autowired
    NewsService newsService;

    @Override
    public News getNews(@PathVariable String id) {
        return newsService.getById(parseID(id));
    }

    @Override
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @Override
    public News addNews(@RequestBody String json) {
        return newsService.addNews(json);
    }

    @Override
    public News editNews(@PathVariable String id, @RequestBody String json) {
        return newsService.editNews(parseID(id), json);
    }

    @Override
    public News deleteNews(@PathVariable String id) {
        return newsService.deleteNews(parseID(id));
    }
}
