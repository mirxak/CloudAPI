package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.entity.base.BaseEntity;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by mirxak on 21.01.15.
 */
@Entity
@Table(name = Complectation.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Complectation extends BaseEntity {

    public static final String CATEGORY = "complectations";
    public static final String TABLE = "complectation";

    //<editor-fold desc="fields">
    private String name;
    private Long engineId;
    private Long bodyId;
    private Long safetyId;
    private Long comfortId;
    private Long gadgetsId;
    private Long carId;
    private Long price;
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
        return new Complectation();
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

    @Column(name = "engine_id", insertable = false, updatable = false)
    public Long getEngineId() {
        return engineId;
    }

    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }

    @Column(name = "body_id", insertable = false, updatable = false)
    public Long getBodyId() {
        return bodyId;
    }

    public void setBodyId(Long bodyId) {
        this.bodyId = bodyId;
    }

    @Column(name = "safety_id", insertable = false, updatable = false)
    public Long getSafetyId() {
        return safetyId;
    }

    public void setSafetyId(Long safetyId) {
        this.safetyId = safetyId;
    }

    @Column(name = "comfort_id", insertable = false, updatable = false)
    public Long getComfortId() {
        return comfortId;
    }

    public void setComfortId(Long comfortId) {
        this.comfortId = comfortId;
    }

    @Column(name = "gadgets_id", insertable = false, updatable = false)
    public Long getGadgetsId() {
        return gadgetsId;
    }

    public void setGadgetsId(Long gadgetsId) {
        this.gadgetsId = gadgetsId;
    }

    @Column(name = "car_id", insertable = false, updatable = false)
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Column(name = "price")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    //<editor-fold desc="Engine">
    private EngineGearbox engineGearbox;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_id")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public EngineGearbox getEngineGearbox() {
        return engineGearbox;
    }

    public void setEngineGearbox(EngineGearbox engineGearbox) {
        this.engineGearbox = engineGearbox;
    }

    //</editor-fold>

    //<editor-fold desc="Body">
    private Body body;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "body_id")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    //</editor-fold>

    //<editor-fold desc="Safety">
    private Safety safety;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "safety_id")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Safety getSafety() {
        return safety;
    }

    public void setSafety(Safety safety) {
        this.safety = safety;
    }
    //</editor-fold>

    //<editor-fold desc="Comfort">
    private Comfort comfort;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comfort_id")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Comfort getComfort() {
        return comfort;
    }

    public void setComfort(Comfort comfort) {
        this.comfort = comfort;
    }

    //</editor-fold>

    //<editor-fold desc="Gadgets">
    private Gadget gadget;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gadget_id")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Gadget getGadget() {
        return gadget;
    }

    public void setGadget(Gadget gadget) {
        this.gadget = gadget;
    }
    //</editor-fold>

    //<editor-fold desc="Car">
    @JsonIgnore
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
        if (car != null){
            setCarId(car.getId());
        }
    }
    //</editor-fold>

}
