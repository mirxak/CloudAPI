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
 * Created by mirxak on 15.03.15.
 */
@Entity
@Table(name = CarService.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarService extends BaseEntity {

    public static final String TABLE = "car_service";
    public static final String CATEGORY = "car_services";

    //<editor-fold desc="fields">
    private String name;
    private String description;
    private Float priceMin;
    private Float priceMax;
    private Integer milage;
    private Integer serviceTime;
    private Long brandId;
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
        return new CarService();
    }

    @Column(name = "name")
    @NotNull(message = "name is null")
    @NotEmpty(message = "name is empty")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "price_min")
    @NotNull(message = "MinPrice is null")
    public Float getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Float priceMin) {
        this.priceMin = priceMin;
    }

    @Column(name = "price_max")
    @NotNull(message = "MaxPrice is null")
    public Float getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Float priceMax) {
        this.priceMax = priceMax;
    }

    @Column(name = "milage")
    public Integer getMilage() {
        return milage;
    }

    public void setMilage(Integer milage) {
        this.milage = milage;
    }

    @Column(name = "service_time")
    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Column(name = "brand_id")
    @NotNull(message = "Brand is null")
    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
