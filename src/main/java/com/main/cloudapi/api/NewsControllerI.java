package com.main.cloudapi.api;

import com.main.cloudapi.entity.News;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mirxak on 04.03.15.
 */
@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"},
                value = "/" + News.CATEGORY)
public interface NewsControllerI {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public News getNews(@PathVariable String id);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<News> getAllNews();

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public News addNews(@RequestBody String json);

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public News editNews(@PathVariable String id, @RequestBody String json);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public News deleteNews(@PathVariable String id);
}
