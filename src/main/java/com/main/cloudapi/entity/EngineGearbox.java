package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.entity.base.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by mirxak on 21.01.15.
 */
@Entity
@Table(name = EngineGearbox.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EngineGearbox extends BaseEntity {

    public static final String CATEGORY = "engine_gearboxes";
    public static final String TABLE = "engine_gearbox";

    //<editor-fold desc="fields">
    private String engine;
    private Integer power;
    private String gearbox;
    private String gearing;
    private Double accelerationToHund;
    private Integer fullSpeed;
    private Integer fuelConsumCity;
    private Integer fuelConsumTrack;
    private Integer fuelConsumMixed;
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
        return new EngineGearbox();
    }

    @Column(name = "engine")
    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @Column(name = "power")
    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    @Column(name = "gearbox")
    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    @Column(name = "gearing")
    public String getGearing() {
        return gearing;
    }

    public void setGearing(String gearing) {
        this.gearing = gearing;
    }

    @Column(name = "acceleration_to_hund")
    public Double getAccelerationToHund() {
        return accelerationToHund;
    }

    public void setAccelerationToHund(Double accelerationToHund) {
        this.accelerationToHund = accelerationToHund;
    }

    @Column(name = "full_speed")
    public Integer getFullSpeed() {
        return fullSpeed;
    }

    public void setFullSpeed(Integer fullSpeed) {
        this.fullSpeed = fullSpeed;
    }

    @Column(name = "fuel_consum_city")
    public Integer getFuelConsumCity() {
        return fuelConsumCity;
    }

    public void setFuelConsumCity(Integer fuelConsumCity) {
        this.fuelConsumCity = fuelConsumCity;
    }

    @Column(name = "fuel_consum_track")
    public Integer getFuelConsumTrack() {
        return fuelConsumTrack;
    }

    public void setFuelConsumTrack(Integer fuelConsumTrack) {
        this.fuelConsumTrack = fuelConsumTrack;
    }

    @Column(name = "fuel_consum_mixed")
    public Integer getFuelConsumMixed() {
        return fuelConsumMixed;
    }

    public void setFuelConsumMixed(Integer fuelConsumMixed) {
        this.fuelConsumMixed = fuelConsumMixed;
    }

    //<editor-fold desc="Complectation">
    @JsonIgnore
    private Complectation complectation;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "engineGearbox")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Complectation getComplectation() {
        return complectation;
    }

    public void setComplectation(Complectation complectation) {
        this.complectation = complectation;
    }
    //</editor-fold>
}
