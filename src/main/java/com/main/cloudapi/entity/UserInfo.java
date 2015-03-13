package com.main.cloudapi.entity;

import com.main.cloudapi.entity.base.BaseEntity;

/**
 * Created by mirxak on 23.01.15.
 */
//@Entity
//@Table(name = Body.TABLE)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo extends BaseEntity {

    public static final String CATEGORY = "users_info";
    public static final String TABLE = "user_info";

    //<editor-fold desc="fields">
    private String firstname;
    private String secondname;
    private String lastname;
    private String email;
    private String phone;
    private Integer age;
    private Integer gender;
    private Integer drivingExperience;
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
        return new UserInfo();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getDrivingExperience() {
        return drivingExperience;
    }

    public void setDrivingExperience(Integer drivingExperience) {
        this.drivingExperience = drivingExperience;
    }
}
