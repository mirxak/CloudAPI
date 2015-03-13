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
@Table(name = Gadget.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gadget extends BaseEntity {

    public static final String CATEGORY = "gadget";
    public static final String TABLE = "gadgets";

    //<editor-fold desc="fields">
    private Integer alloyWheels;
    private Integer metallic;
    private Integer fogLights;
    private Integer xenon;
    private Integer foldingRearSeat1;
    private Integer foldingRearSeat2;
    private Integer tyrePressureSensor;
    private Integer passiveCruiseControl;
    private Integer adaptiveCruiseControl;
    private Integer adaptiveLights;
    private Integer deadZoneControl;
    private Integer autoParking;
    private Integer leatherInterior;
    private Integer seatsActuator;
    private Integer frontMemorySeats;
    private Integer steeringHeat;
    private Integer massagingFrontSeats;
    private Integer massagingRearSeats;
    private Integer seatVentilation;
    private Integer hatch;
    private Integer panaramicGlassRoof;
    private Integer premiumAudio;
    private Integer navigationSystem;
    private Integer eLuggage;
    private Integer phonePreparing;
    private Integer autoParkingBrake;
    private Integer startButton;
    private Integer standalonePreheater;
    private Integer drivedown;
    private Integer lockingCentralDifferential;
    private Integer lockingRearDifferential;
    private Integer lockingFrontDifferential;
    private Integer adjustableClearance;
    private Integer hillStart;
    private Integer hillDescent;
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
        return new Gadget();
    }

    @Column(name = "alloy_wheels")
    public Integer getAlloyWheels() {
        return alloyWheels;
    }

    public void setAlloyWheels(Integer alloyWheels) {
        this.alloyWheels = alloyWheels;
    }

    @Column(name = "metallic")
    public Integer getMetallic() {
        return metallic;
    }

    public void setMetallic(Integer metallic) {
        this.metallic = metallic;
    }

    @Column(name = "fog_lights")
    public Integer getFogLights() {
        return fogLights;
    }

    public void setFogLights(Integer fogLights) {
        this.fogLights = fogLights;
    }

    @Column(name = "xenon")
    public Integer getXenon() {
        return xenon;
    }

    public void setXenon(Integer xenon) {
        this.xenon = xenon;
    }

    @Column(name = "folding_rear_seat1")
    public Integer getFoldingRearSeat1() {
        return foldingRearSeat1;
    }

    public void setFoldingRearSeat1(Integer foldingRearSeat1) {
        this.foldingRearSeat1 = foldingRearSeat1;
    }

    @Column(name = "folding_rear_seat2")
    public Integer getFoldingRearSeat2() {
        return foldingRearSeat2;
    }

    public void setFoldingRearSeat2(Integer foldingRearSeat2) {
        this.foldingRearSeat2 = foldingRearSeat2;
    }

    @Column(name = "tyre_pressure_sensor")
    public Integer getTyrePressureSensor() {
        return tyrePressureSensor;
    }

    public void setTyrePressureSensor(Integer tyrePressureSensor) {
        this.tyrePressureSensor = tyrePressureSensor;
    }

    @Column(name = "passive_cruise_control")
    public Integer getPassiveCruiseControl() {
        return passiveCruiseControl;
    }

    public void setPassiveCruiseControl(Integer passiveCruiseControl) {
        this.passiveCruiseControl = passiveCruiseControl;
    }

    @Column(name = "adaptive_cruise_control")
    public Integer getAdaptiveCruiseControl() {
        return adaptiveCruiseControl;
    }

    public void setAdaptiveCruiseControl(Integer adaptiveCruiseControl) {
        this.adaptiveCruiseControl = adaptiveCruiseControl;
    }

    @Column(name = "adaptive_lights")
    public Integer getAdaptiveLights() {
        return adaptiveLights;
    }

    public void setAdaptiveLights(Integer adaptiveLights) {
        this.adaptiveLights = adaptiveLights;
    }

    @Column(name = "dead_zone_control")
    public Integer getDeadZoneControl() {
        return deadZoneControl;
    }

    public void setDeadZoneControl(Integer deadZoneControl) {
        this.deadZoneControl = deadZoneControl;
    }

    @Column(name = "auto_parking")
    public Integer getAutoParking() {
        return autoParking;
    }

    public void setAutoParking(Integer autoParking) {
        this.autoParking = autoParking;
    }

    @Column(name = "leather_interior")
    public Integer getLeatherInterior() {
        return leatherInterior;
    }

    public void setLeatherInterior(Integer leatherInterior) {
        this.leatherInterior = leatherInterior;
    }

    @Column(name = "seats_actuator")
    public Integer getSeatsActuator() {
        return seatsActuator;
    }

    public void setSeatsActuator(Integer seatsActuator) {
        this.seatsActuator = seatsActuator;
    }

    @Column(name = "front_memory_seats")
    public Integer getFrontMemorySeats() {
        return frontMemorySeats;
    }

    public void setFrontMemorySeats(Integer frontMemorySeats) {
        this.frontMemorySeats = frontMemorySeats;
    }

    @Column(name = "steering_heat")
    public Integer getSteeringHeat() {
        return steeringHeat;
    }

    public void setSteeringHeat(Integer steeringHeat) {
        this.steeringHeat = steeringHeat;
    }

    @Column(name = "massaging_front_seats")
    public Integer getMassagingFrontSeats() {
        return massagingFrontSeats;
    }

    public void setMassagingFrontSeats(Integer massagingFrontSeats) {
        this.massagingFrontSeats = massagingFrontSeats;
    }

    @Column(name = "massaging_rear_seats")
    public Integer getMassagingRearSeats() {
        return massagingRearSeats;
    }

    public void setMassagingRearSeats(Integer massagingRearSeats) {
        this.massagingRearSeats = massagingRearSeats;
    }

    @Column(name = "seat_ventilation")
    public Integer getSeatVentilation() {
        return seatVentilation;
    }

    public void setSeatVentilation(Integer seatVentilation) {
        this.seatVentilation = seatVentilation;
    }

    @Column(name = "hatch")
    public Integer getHatch() {
        return hatch;
    }

    public void setHatch(Integer hatch) {
        this.hatch = hatch;
    }

    @Column(name = "panaramic_glass_roof")
    public Integer getPanaramicGlassRoof() {
        return panaramicGlassRoof;
    }

    public void setPanaramicGlassRoof(Integer panaramicGlassRoof) {
        this.panaramicGlassRoof = panaramicGlassRoof;
    }

    @Column(name = "premium_audio")
    public Integer getPremiumAudio() {
        return premiumAudio;
    }

    public void setPremiumAudio(Integer premiumAudio) {
        this.premiumAudio = premiumAudio;
    }

    @Column(name = "navigation_system")
    public Integer getNavigationSystem() {
        return navigationSystem;
    }

    public void setNavigationSystem(Integer navigationSystem) {
        this.navigationSystem = navigationSystem;
    }

    @Column(name = "e_luggage")
    public Integer geteLuggage() {
        return eLuggage;
    }

    public void seteLuggage(Integer eLuggage) {
        this.eLuggage = eLuggage;
    }

    @Column(name = "phone_preparing")
    public Integer getPhonePreparing() {
        return phonePreparing;
    }

    public void setPhonePreparing(Integer phonePreparing) {
        this.phonePreparing = phonePreparing;
    }

    @Column(name = "auto_parking_brake")
    public Integer getAutoParkingBrake() {
        return autoParkingBrake;
    }

    public void setAutoParkingBrake(Integer autoParkingBrake) {
        this.autoParkingBrake = autoParkingBrake;
    }

    @Column(name = "start_button")
    public Integer getStartButton() {
        return startButton;
    }

    public void setStartButton(Integer startButton) {
        this.startButton = startButton;
    }

    @Column(name = "standalone_preheater")
    public Integer getStandalonePreheater() {
        return standalonePreheater;
    }

    public void setStandalonePreheater(Integer standalonePreheater) {
        this.standalonePreheater = standalonePreheater;
    }

    @Column(name = "drivedown")
    public Integer getDrivedown() {
        return drivedown;
    }

    public void setDrivedown(Integer drivedown) {
        this.drivedown = drivedown;
    }

    @Column(name = "locking_central_differential")
    public Integer getLockingCentralDifferential() {
        return lockingCentralDifferential;
    }

    public void setLockingCentralDifferential(Integer lockingCentralDifferential) {
        this.lockingCentralDifferential = lockingCentralDifferential;
    }

    @Column(name = "locking_rear_differential")
    public Integer getLockingRearDifferential() {
        return lockingRearDifferential;
    }

    public void setLockingRearDifferential(Integer lockingRearDifferential) {
        this.lockingRearDifferential = lockingRearDifferential;
    }

    @Column(name = "locking_front_differential")
    public Integer getLockingFrontDifferential() {
        return lockingFrontDifferential;
    }

    public void setLockingFrontDifferential(Integer lockingFrontDifferential) {
        this.lockingFrontDifferential = lockingFrontDifferential;
    }

    @Column(name = "adjustable_clearance")
    public Integer getAdjustableClearance() {
        return adjustableClearance;
    }

    public void setAdjustableClearance(Integer adjustableClearance) {
        this.adjustableClearance = adjustableClearance;
    }

    @Column(name = "hill_start")
    public Integer getHillStart() {
        return hillStart;
    }

    public void setHillStart(Integer hillStart) {
        this.hillStart = hillStart;
    }

    @Column(name = "hill_descent")
    public Integer getHillDescent() {
        return hillDescent;
    }

    public void setHillDescent(Integer hillDescent) {
        this.hillDescent = hillDescent;
    }

    //<editor-fold desc="Complectation">
    @JsonIgnore
    private Complectation complectation;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "gadget")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Complectation getComplectation() {
        return complectation;
    }

    public void setComplectation(Complectation complectation) {
        this.complectation = complectation;
    }
    //</editor-fold>
}
