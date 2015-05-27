package com.main.cloudapi.api;

import com.main.cloudapi.entity.Mail;
import com.main.cloudapi.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mirxak on 16.05.15.
 */
@RequestMapping(headers = {"Accept=application/json", "Accept=application/v0+json"}, value = "/" + Mail.CATEGORY)
public interface MailControllerI {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Mail> getMails();

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public Mail getMail(@PathVariable String id);

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public Mail sendMail(@RequestBody String json);

    @RequestMapping(value = "/send-list", method = RequestMethod.POST)
    @ResponseBody
    public Mail sendList(@RequestBody String json);

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @ResponseBody
    public Mail editMail(@PathVariable String id, @RequestBody String json);

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Mail deleteMail(@PathVariable String id);
}
