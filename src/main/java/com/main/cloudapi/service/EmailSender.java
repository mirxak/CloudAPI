package com.main.cloudapi.service;

import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.dao.MailDAO;
import com.main.cloudapi.dao.UserDAO;
import com.main.cloudapi.entity.Mail;
import com.main.cloudapi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Created by mirxak on 16.05.15.
 */
@Service
public class EmailSender {

    @Autowired
    MailDAO mailDAO;

    @Autowired
    UserDAO userDAO;

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
//        properties.setProperty("mail.transport.protocol", "smtp");
//        properties.setProperty("mail.smtp.host", MainConfig.get("smtp.host"));
//        properties.put("mail.smtp.auth", MainConfig.get("smtp.auth"));
//        properties.put("mail.smtp.ssl.enable", MainConfig.get("smtp.ssl"));
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(MainConfig.get("smtp.user"), MainConfig.get("smtp.pass"));
//            }
//        });
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("intellectdrive", "bonus20144");
            }
        });

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("intellectdrive@gmail.com"));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getEmail()));
        msg.setSubject(mail.getSubject());
        msg.setText(mail.getMessage());
        Transport.send(msg);

        mail.setIs_sent(1);
        mailDAO.add(mail);
        return mail;
    }

    public Mail sendList(Mail mailtoSend) throws MessagingException {
        List<String> emails = userDAO.getEmails();
        if (emails.isEmpty()){
            throw new ThrowFabric.BadRequestException("There are no users emails");
        }

        for (String email : emails){
            Mail mail = new Mail();
            mail.setSubject(mailtoSend.getSubject());
            mail.setMessage(mailtoSend.getMessage());
            mail.setEmail(email);
            sendMail(mail);
        }

        return mailtoSend;
    }
}
