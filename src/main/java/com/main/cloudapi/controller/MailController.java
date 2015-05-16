package com.main.cloudapi.controller;

import com.main.cloudapi.api.MailControllerI;
import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.controller.base.BaseController;
import com.main.cloudapi.entity.Mail;
import com.main.cloudapi.service.EmailSender;
import com.main.cloudapi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.util.List;

/**
 * Created by mirxak on 16.05.15.
 */
@Controller
public class MailController extends BaseController implements MailControllerI {

    @Autowired
    EmailSender emailSender;

    @Override
    public List<Mail> getMails() {
        return emailSender.getAll();
    }

    @Override
    public Mail getMail(@PathVariable String id) {
        return emailSender.getById(parseID(id));
    }

    @Override
    public Mail sendMail(@RequestBody String json) {
        try {
            return emailSender.sendMail(JsonUtils.getFromJson(json, Mail.class, true));
        } catch (MessagingException e) {
            throw new ThrowFabric.BadRequestException("send mail fail");
        }
    }

    @Override
    public List<Mail> sendList(@RequestBody String json) {
        List<Mail> mails = JsonUtils.getList(json, Mail.class, true);
        try {
            return emailSender.sendList(mails);
        } catch (MessagingException e) {
            throw new ThrowFabric.BadRequestException("send mails fail");
        }
    }

    @Override
    public Mail editMail(@PathVariable String id, @RequestBody String json) {
        return emailSender.editMail(parseID(id), json);
    }

    @Override
    public Mail deleteMail(@PathVariable String id) {
        return emailSender.deleteMail(parseID(id));
    }
}
