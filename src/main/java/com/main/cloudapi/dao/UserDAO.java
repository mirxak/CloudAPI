package com.main.cloudapi.dao;

import com.main.cloudapi.dao.base.BaseDAO;
import com.main.cloudapi.entity.User;
import com.main.cloudapi.utils.UserHashPass;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mirxak on 15.05.15.
 */
@Repository
public class UserDAO extends BaseDAO<User> {
    @Override
    public User getCurrentUser() {
        return null;
    }

    public User getUserByLogin(String login) {
        Session session = getSession();
        Query query = session.createQuery("from " + User.class.getName() + " where login=:login and coalesce(is_deleted,0)<>1 ");
        query.setString("login", login.toLowerCase().trim());
        List<User> listUsers = (List<User>) query.list();
        return !listUsers.isEmpty() ? listUsers.get(0) : new User();
    }

    public String getActivationLink(Long id) {
        Session session=getSession();
        Query query=session.createSQLQuery("select activation_link from users where id=:id and coalesce(is_deleted,0)<>1 ");
        query.setParameter("id",id);
        return (String) query.uniqueResult();
    }

    public boolean setActivationLink(String link,Long userId){
        Session session=getSession();
        Query query = session.createQuery("update users set activation_link=:activation_link where id=:id and coalesce(is_deleted,0)<>1 ");
        query.setString("activation_link", link);
        query.setLong("id", userId);
        return query.executeUpdate()>0;
    }

    public boolean activate(Long id) {
        Session session=getSession();
        Query query = session.createQuery("update users set activation_link='', is_active=1,activation_date=:activation_date where id=:id");
        query.setLong("activation_date", System.currentTimeMillis());
        query.setLong("id", id);
        return query.executeUpdate()>0;
    }

    public User getByIdAndActivationLink(Long id, String activation_link) {
        Criteria c = getSession().createCriteria(User.class);
        c.add(Restrictions.eq("id", id));
        c.add(Restrictions.eq("status",0));
        c.add(Restrictions.sqlRestriction("activation_link=?",activation_link, StringType.INSTANCE));
        User u= (User) c.uniqueResult();
        return u==null?new User():u;
    }

    public User Auth(String login, String password) {
        String salt=getSalt(login);
        String HashPass= UserHashPass.hashPass(password, salt);
        Session session=getSession();

        Query query = session.createQuery("from users where login=:login and password=:password and coalesce(is_deleted,0)<>1 ");
        query.setString("login", login.toLowerCase().trim());
        query.setString("password", HashPass);
        List<User> listUsers = (List<User>)query.list();
        return !listUsers.isEmpty() ?listUsers.get(0):new User();

    }

    @SuppressWarnings("unchecked")
    private String getSalt(String login){
        Session session=getSession();
        Query query = session.createQuery("select salt from User where login=:login and coalesce(is_deleted,0)<>1 ");
        query.setString("login", login.toLowerCase().trim());
        List<String> listSalt = (List<String>)query.list();
        return !listSalt.isEmpty() ?listSalt.get(0):"";

    }
}
