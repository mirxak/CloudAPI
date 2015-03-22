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
@Table(name = Car.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Car extends BaseEntity {

    public static final String CATEGORY = "cars";
    public static final String TABLE = "car";

    //<editor-fold desc="fields">
    private String name;
    private Long brandId;
    private String car_class;
    private String imgUrl;
    private String imgUrl1;
    private String imgUrl2;
    private String imgUrl3;
    private String imgUrl4;
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
//    @NotNull(message = "car is null")
//    @NotEmpty(message = "car is empty")
    public String getCar_class() {
        return car_class;
    }

    public void setCar_class(String car_class) {
        this.car_class = car_class;
    }

    @Column(name = "img_url")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Column(name = "img_url1")
    public String getImgUrl1() {
        return imgUrl1;
    }

    public void setImgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
    }

    @Column(name = "img_url2")
    public String getImgUrl2() {
        return imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    @Column(name = "img_url3")
    public String getImgUrl3() {
        return imgUrl3;
    }

    public void setImgUrl3(String imgUrl3) {
        this.imgUrl3 = imgUrl3;
    }

    @Column(name = "img_url4")
    public String getImgUrl4() {
        return imgUrl4;
    }

    public void setImgUrl4(String imgUrl4) {
        this.imgUrl4 = imgUrl4;
    }

    //<editor-fold desc="Brand">
    @JsonIgnore
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Brand getBrand() {
        if (brand == null){
            return UnProxierLazyObjects.unproxy(brand);
        }
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
    private Set<Complectation> complectations = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Set<Complectation> getComplectations() {
        if ((complectations == null) || (complectations.isEmpty())){
            return new LinkedHashSet<>(UnProxierLazyObjects.unproxy(complectations));
        }
        return complectations;
    }

    public void setComplectations(Set<Complectation> complectations) {
        this.complectations = complectations;
    }

    //</editor-fold>
}
