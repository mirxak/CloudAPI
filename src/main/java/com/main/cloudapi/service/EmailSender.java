package com.main.cloudapi.service;

import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.dao.MailDAO;
import com.main.cloudapi.entity.Mail;
import com.main.cloudapi.utils.JsonUtils;
import com.main.cloudapi.utils.MainConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by mirxak on 16.05.15.
 */
@Service
public class EmailSender {

    @Autowired
    MailDAO mailDAO;

    public List<Mail> getAll(){
        return mailDAO.getAll(0, 10000);
    }

    public Mail getById(Long id){
        return mailDAO.getById(id);
    }

    @Transactional
    public Mail editMail(Long id, String json){
        Mail mail = getById(id);
        JsonUtils.update(json, mail, true);
        return mailDAO.update(mail);
    }

    @Transactional
    public Mail deleteMail(Long id){
        Mail mail = getById(id);
        mailDAO.delete(mail);
        return mail;
    }

    @Transactional
    public Mail sendMail(Mail mail) throws MessagingException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", MainConfig.get("smtp.host"));
        properties.put("mail.smtp.auth", MainConfig.get("smtp.auth"));
        properties.put("mail.smtp.ssl.enable", MainConfig.get("smtp.ssl"));
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MainConfig.get("smtp.user"), MainConfig.get("smtp.pass"));
            }
        });

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(MainConfig.get("main.from")));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getEmail()));
        msg.setSubject(mail.getSubject());
        msg.setText(mail.getMessage());
        Transport.send(msg);

        mail.setIs_sent(1);
        mailDAO.add(mail);
        return mail;
    }

    public List<Mail> sendList(List<Mail> mails) throws MessagingException {
        List<Mail> result = new ArrayList<>();
        for (Mail mail : mails){
            result.add(sendMail(mail));
        }
        return result;
    }
}
