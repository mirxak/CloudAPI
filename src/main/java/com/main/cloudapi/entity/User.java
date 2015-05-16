package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.entity.base.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by mirxak on 23.01.15.
 */
@Entity
@Table(name = User.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseEntity {

    public static final String CATEGORY = "user";
    public static final String TABLE = "users";

    //<editor-fold desc="fields">
    private String login;
    private String pass;
    private String firstname;
    private String secondname;
    private String lastname;
    private String email;
    private String phone;
    private Integer age;
    private Integer gender;
    private Integer drivingExperience;
    private Integer status;
    private Long birthDate;
    private String salt;
    private Integer is_active;
    private String activation_link;
    private String access_token;
    private String activation_date;
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

    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "pass")
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "secondname")
    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "gender")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Column(name = "driving_experience")
    public Integer getDrivingExperience() {
        return drivingExperience;
    }

    public void setDrivingExperience(Integer drivingExperience) {
        this.drivingExperience = drivingExperience;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "birth_date")
    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "salt")
    @JsonIgnore
    public String getSalt() {
        return salt;
    }

    @JsonIgnore
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Column(name = "is_active")
    public Integer getIs_active() {
        return is_active;
    }

    public void setIs_active(Integer is_active) {
        this.is_active = is_active;
    }

    @Column(name = "activation_link")
    public String getActivation_link() {
        return activation_link;
    }

    public void setActivation_link(String activation_link) {
        this.activation_link = activation_link;
    }

    @Column(name = "access_token")
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Column(name = "activation_date")
    public String getActivation_date() {
        return activation_date;
    }

    public void setActivation_date(String activation_date) {
        this.activation_date = activation_date;
    }
}
