package com.main.cloudapi.service;

import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.dao.UserDAO;
import com.main.cloudapi.entity.Mail;
import com.main.cloudapi.entity.User;
import com.main.cloudapi.utils.ContextHolder;
import com.main.cloudapi.utils.JsonUtils;
import com.main.cloudapi.utils.TokenUtils;
import com.main.cloudapi.utils.UserHashPass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mirxak on 15.05.15.
 */
@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    EmailSender emailSender;

    @Autowired
    TokenUtils tokenUtils;

    public User register(String json){
        User user = JsonUtils.getFromJson(json, User.class, true);
        validateRegister(user);

        user.setLogin(user.getEmail());
        UserHashPass.securePass(user);

        add(user);

        String activation_link = UserHashPass.hashPass(UserHashPass.genPass(5) + System.currentTimeMillis());
        userDAO.setActivationLink(activation_link, user.getId());
        //activation

        sendActivationLink(activation_link, user, new HashMap<String, String>());
        return user;
    }

    public User getCurUser(){
        return ContextHolder.getUser();
    }

    public User login(String json){
        User user = JsonUtils.getFromJson(json, User.class, true);
        User u=userDAO.getUserByLogin(user.getLogin());
        if(u.getId()!=null){
            if(u.getIs_active()==0){
                String link=userDAO.getActivationLink(u.getId());
                sendActivationLink(link,u,new HashMap<String, String>());
                throw new ThrowFabric.ForbiddenException("not activated user");
            }
        }

        u = userDAO.Auth(user.getLogin(), user.getPass());
        if (u != null && u.getId() != null && u.getId() > 0) {
            u.setAccess_token(tokenUtils.getToken(u));
        }

        ContextHolder.setUser(user);

        return user;
    }

    @Transactional
    public User activate(Long id, String activateLink) {

        User u = userDAO.getByIdAndActivationLink(id, activateLink);

        if (u == null || u.getId() == null) {
            throw new ThrowFabric.ResourceNotFoundException("user not exist");
        }
        if (u.getIs_active() == 1) {
            throw new ThrowFabric.LockedException("already.activated.entity", User.class.getSimpleName());
        }
        userDAO.activate(id);
        userDAO.evict(u);
        ContextHolder.setUser(u);


        Mail mail = new Mail();
        mail.setIs_sent(0);
        mail.setSubject("Activation");
        mail.setMessage("Activated");
        mail.setEmail(u.getEmail());

        try {
            emailSender.sendMail(mail);
        } catch (MessagingException e) {
            throw new ThrowFabric.BadRequestException("send mail fail");
        }
//        Notifications notifications = getUserNotifications(MessageTemplates.USER_0_ACTIVATION, u);
//        notifications.setData(getUserData(u,new HashMap<String, String>()));
//        producer.sendMail(notifications);

        u = userDAO.getById(id);
//        u.setAccess_token(tokenUtils.getToken(u));
//        if (u.getStatus() == 0) {
//            JsonWebContext.addMapperView(UserView.STATUS_0.class);
//        }
        return u;
    }

    private void sendActivationLink(String Link, User user, Map<String, String> data) {
        Mail mail = new Mail();
        mail.setIs_sent(0);
        mail.setEmail(user.getEmail());
        mail.setMessage(Link);
        mail.setSubject("Activation");
        try {
            emailSender.sendMail(mail);
        } catch (MessagingException e) {
            throw new ThrowFabric.BadRequestException("send mail fail");
        }

//        Notifications notifications = getUserNotifications(MessageTemplates.USER_0_REGISTRATION, user);
//        if (data == null) {
//            data = new HashMap<>();
//        }
//        data.put("user.activation_link", Link);
//        notifications.setData(getUserData(user, data));
//        producer.sendMail(notifications);
    }

    @Transactional
    private User add(User user){
        userDAO.add(user);
        return user;
    }

    private void validateRegister(User user){
        if (StringUtils.isBlank(user.getEmail())){
            throw new ThrowFabric.BadRequestException("email is empty");
        }

        if (StringUtils.isBlank(user.getPass())){
            throw new ThrowFabric.BadRequestException("pass is empty");
        }
        else if (user.getPass().length() < 8){
            throw new ThrowFabric.BadRequestException("pass have to be more than 8 symbols");
        }

        if (StringUtils.isBlank(user.getFirstname())){
            throw new ThrowFabric.BadRequestException("firstname is empty");
        }

        if (user.getGender() == null){
            throw new ThrowFabric.BadRequestException("gender is null");
        }

        if (user.getBirthDate() == null){
            throw new ThrowFabric.BadRequestException("birth_date is null");
        }

        if (user.getDrivingExperience() == null){
            throw new ThrowFabric.BadRequestException("driving_experience is null");
        }
    }

}
