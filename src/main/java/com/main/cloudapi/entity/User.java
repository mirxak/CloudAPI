package com.main.cloudapi.entity;

import com.main.cloudapi.entity.base.BaseEntity;

/**
 * Created by mirxak on 23.01.15.
 */
//@Entity
//@Table(name = Body.TABLE)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseEntity {

    public static final String CATEGORY = "user";
    public static final String TABLE = "users";

    //<editor-fold desc="fields">
    private String login;
    private String pass;
    private Long userInfoId;
    //</editor-fold>

    @Override
    public String getTable() {
        return TABLE;
    }

    @Override
    public String getObjectCategory() {
        return CATEGORY;
    }

    @Override
    public BaseEntity generateEntity() {
        return new User();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }
}
