package com.main.cloudapi.entity;

/**
 * Created by mirxak on 22.03.15.
 */
// Входные параметры для расчетов
public class CalcFilter {

    //<editor-fold desc="fields">
    private Long brand_id;
    private Integer volumeMin;
    private Integer volumeMax;
    private Integer powerMin;
    private Integer powerMax;
    private String gearbox;
    private String gearing;
    private Float accelerationToHundMin;
    private Float accelerationToHundMax;
    private Integer fullSpeedMin;
    private Integer fullSpeedMax;
    private Integer fuelConsumCityMin;
    private Integer fuelConsumCityMax;
    private Integer fuelConsumTrackMin;
    private Integer fuelConsumTrackMax;
    private Integer fuelConsumMixedMin;
    private Integer fuelConsumMixedMax;
    private Integer clearanceMin;
    private Integer clearanceMax;
    private Integer luggageAmountMin;
    private Integer luggageAmountMax;
    private Integer fuelCapacityMin;
    private Integer fuelCapacityMax;
    private Long complectationPriceMin;
    private Long complectationPriceMax;
    //</editor-fold>

    public Long getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Long brand_id) {
        this.brand_id = brand_id;
    }

    public Integer getVolumeMin() {
        return volumeMin;
    }

    public void setVolumeMin(Integer volumeMin) {
        this.volumeMin = volumeMin;
    }

    public Integer getVolumeMax() {
        return volumeMax;
    }

    public void setVolumeMax(Integer volumeMax) {
        this.volumeMax = volumeMax;
    }

    public Integer getPowerMin() {
        return powerMin;
    }

    public void setPowerMin(Integer powerMin) {
        this.powerMin = powerMin;
    }

    public Integer getPowerMax() {
        return powerMax;
    }

    public void setPowerMax(Integer powerMax) {
        this.powerMax = powerMax;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public String getGearing() {
        return gearing;
    }

    public void setGearing(String gearing) {
        this.gearing = gearing;
    }

    public Float getAccelerationToHundMin() {
        return accelerationToHundMin;
    }

    public void setAccelerationToHundMin(Float accelerationToHundMin) {
        this.accelerationToHundMin = accelerationToHundMin;
    }

    public Float getAccelerationToHundMax() {
        return accelerationToHundMax;
    }

    public void setAccelerationToHundMax(Float accelerationToHundMax) {
        this.accelerationToHundMax = accelerationToHundMax;
    }

    public Integer getFullSpeedMin() {
        return fullSpeedMin;
    }

    public void setFullSpeedMin(Integer fullSpeedMin) {
        this.fullSpeedMin = fullSpeedMin;
    }

    public Integer getFullSpeedMax() {
        return fullSpeedMax;
    }

    public void setFullSpeedMax(Integer fullSpeedMax) {
        this.fullSpeedMax = fullSpeedMax;
    }

    public Integer getFuelConsumCityMin() {
        return fuelConsumCityMin;
    }

    public void setFuelConsumCityMin(Integer fuelConsumCityMin) {
        this.fuelConsumCityMin = fuelConsumCityMin;
    }

    public Integer getFuelConsumCityMax() {
        return fuelConsumCityMax;
    }

    public void setFuelConsumCityMax(Integer fuelConsumCityMax) {
        this.fuelConsumCityMax = fuelConsumCityMax;
    }

    public Integer getFuelConsumTrackMin() {
        return fuelConsumTrackMin;
    }

    public void setFuelConsumTrackMin(Integer fuelConsumTrackMin) {
        this.fuelConsumTrackMin = fuelConsumTrackMin;
    }

    public Integer getFuelConsumTrackMax() {
        return fuelConsumTrackMax;
    }

    public void setFuelConsumTrackMax(Integer fuelConsumTrackMax) {
        this.fuelConsumTrackMax = fuelConsumTrackMax;
    }

    public Integer getFuelConsumMixedMin() {
        return fuelConsumMixedMin;
    }

    public void setFuelConsumMixedMin(Integer fuelConsumMixedMin) {
        this.fuelConsumMixedMin = fuelConsumMixedMin;
    }

    public Integer getFuelConsumMixedMax() {
        return fuelConsumMixedMax;
    }

    public void setFuelConsumMixedMax(Integer fuelConsumMixedMax) {
        this.fuelConsumMixedMax = fuelConsumMixedMax;
    }

    public Integer getClearanceMin() {
        return clearanceMin;
    }

    public void setClearanceMin(Integer clearanceMin) {
        this.clearanceMin = clearanceMin;
    }

    public Integer getClearanceMax() {
        return clearanceMax;
    }

    public void setClearanceMax(Integer clearanceMax) {
        this.clearanceMax = clearanceMax;
    }

    public Integer getLuggageAmountMin() {
        return luggageAmountMin;
    }

    public void setLuggageAmountMin(Integer luggageAmountMin) {
        this.luggageAmountMin = luggageAmountMin;
    }

    public Integer getLuggageAmountMax() {
        return luggageAmountMax;
    }

    public void setLuggageAmountMax(Integer luggageAmountMax) {
        this.luggageAmountMax = luggageAmountMax;
    }

    public Integer getFuelCapacityMin() {
        return fuelCapacityMin;
    }

    public void setFuelCapacityMin(Integer fuelCapacityMin) {
        this.fuelCapacityMin = fuelCapacityMin;
    }

    public Integer getFuelCapacityMax() {
        return fuelCapacityMax;
    }

    public void setFuelCapacityMax(Integer fuelCapacityMax) {
        this.fuelCapacityMax = fuelCapacityMax;
    }

    public Long getComplectationPriceMin() {
        return complectationPriceMin;
    }

    public void setComplectationPriceMin(Long complectationPriceMin) {
        this.complectationPriceMin = complectationPriceMin;
    }

    public Long getComplectationPriceMax() {
        return complectationPriceMax;
    }

    public void setComplectationPriceMax(Long complectationPriceMax) {
        this.complectationPriceMax = complectationPriceMax;
    }
}
