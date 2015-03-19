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
 * Created by mirxak on 20.03.15.
 */
@Entity
@Table(name = Settings.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Settings extends BaseEntity {

    public static final String TABLE = "settings";
    public static final String CATEGORY = "all_settings";

    //<editor-fold desc="fields">
    private String name;
    private String setting;
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
        return new Settings();
    }

    @NotNull(message = "name is null")
    @NotEmpty(message = "name is empty")
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "setting is null")
    @NotEmpty(message = "setting is empty")
    @Column(name = "setting")
    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }
}
