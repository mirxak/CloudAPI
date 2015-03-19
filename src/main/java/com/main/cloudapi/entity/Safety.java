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
@Table(name = Safety.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Safety extends BaseEntity {

    public static final String CATEGORY = "safeties";
    public static final String TABLE = "safety";

    //<editor-fold desc="fields">
    private Integer abs;
    private Integer esp;
    private Integer airbagsCount;
    private Integer driverAirbag;
    private Integer frontPassAirbag;
    private Integer frontSideAirbag;
    private Integer rearSideAirbag;
    private Integer frontPillows;
    private Integer secondSeatsPillows;
    private Integer airbagDriverLaps;
    private Integer airbagPassLaps;
    private Integer parkingSensor;
    private Integer rearViewCam;
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
        return new Safety();
    }

    @Column(name = "abs")
    public Integer getAbs() {
        return abs;
    }

    public void setAbs(Integer abs) {
        this.abs = abs;
    }

    @Column(name = "esp")
    public Integer getEsp() {
        return esp;
    }

    public void setEsp(Integer esp) {
        this.esp = esp;
    }

    @Column(name = "airbags_count")
    public Integer getAirbagsCount() {
        return airbagsCount;
    }

    public void setAirbagsCount(Integer airbagsCount) {
        this.airbagsCount = airbagsCount;
    }

    @Column(name = "driver_airbag")
    public Integer getDriverAirbag() {
        return driverAirbag;
    }

    public void setDriverAirbag(Integer driverAirbag) {
        this.driverAirbag = driverAirbag;
    }

    @Column(name = "front_pass_airbag")
    public Integer getFrontPassAirbag() {
        return frontPassAirbag;
    }

    public void setFrontPassAirbag(Integer frontPassAirbag) {
        this.frontPassAirbag = frontPassAirbag;
    }

    @Column(name = "front_side_airbag")
    public Integer getFrontSideAirbag() {
        return frontSideAirbag;
    }

    public void setFrontSideAirbag(Integer frontSideAirbag) {
        this.frontSideAirbag = frontSideAirbag;
    }

    @Column(name = "rear_side_airbag")
    public Integer getRearSideAirbag() {
        return rearSideAirbag;
    }

    public void setRearSideAirbag(Integer rearSideAirbag) {
        this.rearSideAirbag = rearSideAirbag;
    }

    @Column(name = "front_pillows")
    public Integer getFrontPillows() {
        return frontPillows;
    }

    public void setFrontPillows(Integer frontPillows) {
        this.frontPillows = frontPillows;
    }

    @Column(name = "second_seats_pillows")
    public Integer getSecondSeatsPillows() {
        return secondSeatsPillows;
    }

    public void setSecondSeatsPillows(Integer secondSeatsPillows) {
        this.secondSeatsPillows = secondSeatsPillows;
    }

    @Column(name = "airbag_driver_laps")
    public Integer getAirbagDriverLaps() {
        return airbagDriverLaps;
    }

    public void setAirbagDriverLaps(Integer airbagDriverLaps) {
        this.airbagDriverLaps = airbagDriverLaps;
    }

    @Column(name = "airbag_pass_laps")
    public Integer getAirbagPassLaps() {
        return airbagPassLaps;
    }

    public void setAirbagPassLaps(Integer airbagPassLaps) {
        this.airbagPassLaps = airbagPassLaps;
    }

    @Column(name = "parking_sensor")
    public Integer getParkingSensor() {
        return parkingSensor;
    }

    public void setParkingSensor(Integer parkingSensor) {
        this.parkingSensor = parkingSensor;
    }

    @Column(name = "rear_view_cam")
    public Integer getRearViewCam() {
        return rearViewCam;
    }

    public void setRearViewCam(Integer rearViewCam) {
        this.rearViewCam = rearViewCam;
    }

    //<editor-fold desc="Complectation">
    @JsonIgnore
    private Complectation complectation;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "safety")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Complectation getComplectation() {
        return complectation;
    }

    public void setComplectation(Complectation complectation) {
        this.complectation = complectation;
    }
    //</editor-fold>
}
