package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by mirxak on 22.03.15.
 */
@Entity
@Table(name = PriceCoef.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceCoef extends BaseEntity {

    public static final String TABLE = "price_coef";
    public static final String CATEGORY = "price-coefs";

    //<editor-fold desc="fields">
    private Float priceMin;
    private Float priceMax;
    private Float coef;
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
        return new PriceCoef();
    }

    @Column(name = "price_min")
    public Float getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Float priceMin) {
        this.priceMin = priceMin;
    }

    @Column(name = "price_max")
    public Float getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Float priceMax) {
        this.priceMax = priceMax;
    }

    @Column(name = "coef")
    public Float getCoef() {
        return coef;
    }

    public void setCoef(Float coef) {
        this.coef = coef;
    }
}
