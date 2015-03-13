package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.entity.base.BaseEntity;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mirxak on 21.01.15.
 */
@Entity
@Table(name = Car.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Car extends BaseEntity {

    public static final String CATEGORY = "cars";
    public static final String TABLE = "car";

    //<editor-fold desc="fields">
    private String name;
    private Long brandId;
    private String car_class;
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
        return new Car();
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

    @Column(name = "brand_id", insertable = false, updatable = false)
    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    @Column(name = "car_class")
    @NotNull(message = "car is null")
    @NotEmpty(message = "car is empty")
    public String getCar_class() {
        return car_class;
    }

    public void setCar_class(String car_class) {
        this.car_class = car_class;
    }

    //<editor-fold desc="Brand">
    @JsonIgnore
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
        if (brand != null){
            setBrandId(brand.getId());
        }
    }
    //</editor-fold>

    //<editor-fold desc="Complectations">
    @JsonIgnore
    private Set<Complectation> cars = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Set<Complectation> getCars() {
        return cars;
    }

    public void setCars(Set<Complectation> cars) {
        this.cars = cars;
    }

    //</editor-fold>
}
