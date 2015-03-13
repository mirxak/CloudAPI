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
@Table(name = Comfort.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comfort extends BaseEntity {
    public static final String CATEGORY = "comforts";
    public static final String TABLE = "comfort";

    //<editor-fold desc="fields">
    private Integer conditioner;
    private Integer climateControl;
    private Integer boardComputer;
    private Integer lightSensor;
    private Integer rainSensor;
    private Integer audioCd;
    private Integer audioCdMp3;
    private Integer cdChanger;
    private Integer powerSteering;
    private Integer centralLocking;
    private Integer duCentralLocking;
    private Integer electricalMirrors;
    private Integer mirrorsHeating;
    private Integer frontEwindows;
    private Integer rearEwindows;
    private Integer seatsHeating;
    private Integer adjustableSteering;
    private Integer adjustableDriverSeat;
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
        return new Comfort();
    }

    @Column(name = "conditioner")
    public Integer getConditioner() {
        return conditioner;
    }

    public void setConditioner(Integer conditioner) {
        this.conditioner = conditioner;
    }

    @Column(name = "climate_control")
    public Integer getClimateControl() {
        return climateControl;
    }

    public void setClimateControl(Integer climateControl) {
        this.climateControl = climateControl;
    }

    @Column(name = "board_computer")
    public Integer getBoardComputer() {
        return boardComputer;
    }

    public void setBoardComputer(Integer boardComputer) {
        this.boardComputer = boardComputer;
    }

    @Column(name = "light_sensor")
    public Integer getLightSensor() {
        return lightSensor;
    }

    public void setLightSensor(Integer lightSensor) {
        this.lightSensor = lightSensor;
    }

    @Column(name = "rain_sensor")
    public Integer getRainSensor() {
        return rainSensor;
    }

    public void setRainSensor(Integer rainSensor) {
        this.rainSensor = rainSensor;
    }

    @Column(name = "audio_cd")
    public Integer getAudioCd() {
        return audioCd;
    }

    public void setAudioCd(Integer audioCd) {
        this.audioCd = audioCd;
    }

    @Column(name = "audio_cd_mp3")
    public Integer getAudioCdMp3() {
        return audioCdMp3;
    }

    public void setAudioCdMp3(Integer audioCdMp3) {
        this.audioCdMp3 = audioCdMp3;
    }

    @Column(name = "cd_changer")
    public Integer getCdChanger() {
        return cdChanger;
    }

    public void setCdChanger(Integer cdChanger) {
        this.cdChanger = cdChanger;
    }

    @Column(name = "power_steering")
    public Integer getPowerSteering() {
        return powerSteering;
    }

    public void setPowerSteering(Integer powerSteering) {
        this.powerSteering = powerSteering;
    }

    @Column(name = "central_locking")
    public Integer getCentralLocking() {
        return centralLocking;
    }

    public void setCentralLocking(Integer centralLocking) {
        this.centralLocking = centralLocking;
    }

    @Column(name = "du_central_locking")
    public Integer getDuCentralLocking() {
        return duCentralLocking;
    }

    public void setDuCentralLocking(Integer duCentralLocking) {
        this.duCentralLocking = duCentralLocking;
    }

    @Column(name = "electrical_mirrors")
    public Integer getElectricalMirrors() {
        return electricalMirrors;
    }

    public void setElectricalMirrors(Integer electricalMirrors) {
        this.electricalMirrors = electricalMirrors;
    }

    @Column(name = "mirrors_heating")
    public Integer getMirrorsHeating() {
        return mirrorsHeating;
    }

    public void setMirrorsHeating(Integer mirrorsHeating) {
        this.mirrorsHeating = mirrorsHeating;
    }

    @Column(name = "front_ewindows")
    public Integer getFrontEwindows() {
        return frontEwindows;
    }

    public void setFrontEwindows(Integer frontEwindows) {
        this.frontEwindows = frontEwindows;
    }

    @Column(name = "rear_ewindows")
    public Integer getRearEwindows() {
        return rearEwindows;
    }

    public void setRearEwindows(Integer rearEwindows) {
        this.rearEwindows = rearEwindows;
    }

    @Column(name = "seats_heating")
    public Integer getSeatsHeating() {
        return seatsHeating;
    }

    public void setSeatsHeating(Integer seatsHeating) {
        this.seatsHeating = seatsHeating;
    }

    @Column(name = "adjustable_steering")
    public Integer getAdjustableSteering() {
        return adjustableSteering;
    }

    public void setAdjustableSteering(Integer adjustableSteering) {
        this.adjustableSteering = adjustableSteering;
    }

    @Column(name = "adjustable_driver_seat")
    public Integer getAdjustableDriverSeat() {
        return adjustableDriverSeat;
    }

    public void setAdjustableDriverSeat(Integer adjustableDriverSeat) {
        this.adjustableDriverSeat = adjustableDriverSeat;
    }

    //<editor-fold desc="Complectation">
    @JsonIgnore
    private Complectation complectation;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "comfort")
    @Where(clause = "coalesce(is_deleted,0) <> 1")
    public Complectation getComplectation() {
        return complectation;
    }

    public void setComplectation(Complectation complectation) {
        this.complectation = complectation;
    }
    //</editor-fold>
}
