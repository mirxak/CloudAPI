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

    //<editor-fold desc="Car">
    @JsonIgnore
    private Set<Car> cars = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
    //</editor-fold>

}
