package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.entity.base.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Created by mirxak on 16.05.15.
 */
@Entity
@Table(name = Mail.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mail extends BaseEntity {

    public static final String TABLE = "mails";
    public static final String CATEGORY = "emails";

    //<editor-fold desc="fields">
    private String message;
    private String email;
    private Integer is_sent;
    private String subject;
    //</editor-fold>

    @Override
    @Transient
    public String getTable() {
        return TABLE;
    }

    @Override
    @Transient
    public String getObjectCategory() {
        return CATEGORY;
    }

    @Override
    public BaseEntity generateEntity() {
        return new Mail();
    }

    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    @NotNull(message = "email is null")
//    @NotEmpty(message = "email is empty")
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "is_sent")
    public Integer getIs_sent() {
        return (is_sent==null) ? 0 : is_sent;
    }

    public void setIs_sent(Integer is_sent) {
        this.is_sent = is_sent;
    }

    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
