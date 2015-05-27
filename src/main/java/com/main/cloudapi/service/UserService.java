package com.main.cloudapi.service;

import com.main.cloudapi.constmes.ThrowFabric;
import com.main.cloudapi.dao.UserDAO;
import com.main.cloudapi.entity.Mail;
import com.main.cloudapi.entity.User;
import com.main.cloudapi.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mirxak on 15.05.15.
 */
@Service
public class UserService {

    public static final String BASE_URL = "http://intellect-drive.ru:8080/cloudapi-1.0/rest/api/cloudapi/user/";

    @Autowired
    UserDAO userDAO;

    @Autowired
    EmailSender emailSender;

    @Autowired
    TokenUtils tokenUtils;

    public boolean checkPermission(Long id){
        User curUser = getCurUser();
        if ((curUser == null) || (curUser.getId() == null)){
            return false;
        }

        if ((!curUser.getId().equals(id)) && (curUser.getStatus() != 2)){
            return false;
        }

        return true;
    }

    public User getUser(Long id){
        if (!checkPermission(id)){
            throw new ThrowFabric.LockedException("Permission denied");
        }

        return userDAO.getById(id, User.class);
    }

    public void validateToken(String token){
        User user = userDAO.getByToken(token);
        if (user.getId() == null){
            throw new ThrowFabric.UnAuthorizedException("token is invalid");
        }

        ContextHolder.setUser(user);
    }

    public List<User> getUsers(){
        User curUser = getCurUser();
        if ((curUser == null) || (curUser.getId() == null)){
            throw new ThrowFabric.LockedException("Permission denied");
        }

        if (curUser.getStatus() != 2){
            return Collections.singletonList(userDAO.getById(curUser.getId(), User.class));
        }

        return userDAO.getAll(User.class, 0, 10000);
    }

    @Transactional
    public User editUser(Long id, String json){
        if (!checkPermission(id)){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        User editU = JsonUtils.getFromJson(json, User.class, true);
        User u = userDAO.getById(id, User.class);
        u.setDrivingExperience(editU.getDrivingExperience());
        u.setEmail(editU.getEmail());
        u.setGender(editU.getGender());
        u.setAge(editU.getAge());
        u.setBirthDate(editU.getBirthDate());
        u.setFirstname(editU.getFirstname());
        u.setLastname(editU.getLastname());
        u.setSecondname(editU.getSecondname());
        u.setPhone(editU.getPhone());

        return userDAO.update(u);
    }

    @Transactional
    public User deleteUser(Long id){
        if (!checkPermission(id)){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        User u = userDAO.getById(id, User.class);

        userDAO.delete(u);
        return u;
    }

    public User register(String json){
        User user = JsonUtils.getFromJson(json, User.class, true);
        validateRegister(user);
        user.setLogin(user.getEmail());

        User isExist = userDAO.getUserByLogin(user.getLogin());
        if (isExist.getId()!=null){
            throw new ThrowFabric.BadRequestException("user is already exist with this login");
        }

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
            userDAO.update(u);
        }

        ContextHolder.setUser(u);

        return u;
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

//        u = userDAO.getById(id);
//        u.setAccess_token(tokenUtils.getToken(u));
//        if (u.getStatus() == 0) {
//            JsonWebContext.addMapperView(UserView.STATUS_0.class);
//        }
        return u;
    }

    @Transactional
    public User exit(Long id){
        if (!checkPermission(id)){
            throw new ThrowFabric.LockedException("Permission denied");
        }
        User u = userDAO.getById(id, User.class);

        u.setAccess_token("");
        userDAO.update(u);
        ContextHolder.setUser(null);
        return u;
    }

    private void sendActivationLink(String link, User user, Map<String, String> data) {
//        if (StringUtils.isBlank(ContextHolder.getData().getActivationUrl())){
//            throw new ThrowFabric.BadRequestException("activation.base.url is empty");
//        }
//        String completeLink = ContextHolder.getData().getActivationUrl() + user.getId() +
//                              "/activate/" + link;

        String completeLink = BASE_URL + user.getId() + "/activate/" + link;

        Mail mail = new Mail();
        mail.setIs_sent(0);
        mail.setEmail(user.getEmail());
        mail.setMessage(completeLink);
        mail.setSubject("Activation");
        try {
            emailSender.sendMail(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
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
