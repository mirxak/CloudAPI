package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.entity.base.BaseEntity;
import com.main.cloudapi.utils.UnProxierLazyObjects;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by mirxak on 21.01.15.
 */
@Entity
@Table(name = Brand.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Brand extends BaseEntity {

    public static final String CATEGORY = "brands";
    public static final String TABLE = "brand";

    //<editor-fold desc="fields">
    @Column(name = "name")
    @NotNull(message = "name is null")
    @NotEmpty(message = "name is empty")
    private String name;

    private Float serviceCoef;
    private String imgUrl;
    private Float creditPercent;
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
        return new Brand();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "service_coef")
    public Float getServiceCoef() {
        return serviceCoef;
    }

    public void setServiceCoef(Float serviceCoef) {
        this.serviceCoef = serviceCoef;
    }

    @Column(name = "img_url")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Column(name = "credit_percent")
    public Float getCreditPercent() {
        return creditPercent;
    }

    public void setCreditPercent(Float creditPercent) {
        this.creditPercent = creditPercent;
    }

    //<editor-fold desc="Car">
    @JsonIgnore
    private Set<Car> cars = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Set<Car> getCars() {
        if ((cars == null) || (cars.isEmpty())){
            return new LinkedHashSet<>(UnProxierLazyObjects.unproxy(cars));
        }
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    //</editor-fold>

}
