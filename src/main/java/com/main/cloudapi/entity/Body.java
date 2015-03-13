package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.entity.base.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mirxak on 21.01.15.
 */
@Entity
@Table(name = Body.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Body extends BaseEntity {

    public static final String CATEGORY = "bodies";
    public static final String TABLE = "body";

    //<editor-fold desc="fields">
    private Integer clearance;
    private String dimensions;
    private Integer luggageAmount;
    private Integer fuelCapacity;
    private Integer weight;
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
        return new Body();
    }

    @Column(name = "clearance")
    public Integer getClearance() {
        return clearance;
    }

    public void setClearance(Integer clearance) {
        this.clearance = clearance;
    }

    @Column(name = "dimensions")
    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    @Column(name = "luggage_amount")
    public Integer getLuggageAmount() {
        return luggageAmount;
    }

    public void setLuggageAmount(Integer luggageAmount) {
        this.luggageAmount = luggageAmount;
    }

    @Column(name = "fuel_capacity")
    public Integer getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(Integer fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    @Column(name = "weight")
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    //<editor-fold desc="Complectation">
    @JsonIgnore
    private Complectation complectation;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "body")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Complectation getComplectation() {
        return complectation;
    }

    public void setComplectation(Complectation complectation) {
        this.complectation = complectation;
    }
    //</editor-fold>

}
