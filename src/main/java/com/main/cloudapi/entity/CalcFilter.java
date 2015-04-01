package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mirxak on 22.03.15.
 */
// Входные параметры для расчетов
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalcFilter {

    //<editor-fold desc="fields">
    private Long brand_id; // id марки
    private Integer volumeMin; // минимальный объем двигателя
    private Integer volumeMax; // максимальный объем двигателя
    private Integer powerMin; // мин мощность
    private Integer powerMax; // макс мощность
    private List<String> gearbox; // вид коробки передач
    private List<String> gearing; // привод
    private Float accelerationToHundMin; // мин разгон до 100
    private Float accelerationToHundMax; // макс разгон до 100
    private Integer fullSpeedMin; // макс скорость нижняя граница
    private Integer fullSpeedMax; // макс скорость верхняя граница
    private Integer fuelConsumCityMin;
    private Integer fuelConsumCityMax;
    private Integer fuelConsumTrackMin;
    private Integer fuelConsumTrackMax;
    private Integer fuelConsumMixedMin;
    private Integer fuelConsumMixedMax;
    private Integer clearanceMin; // дорожный просвет
    private Integer clearanceMax;
    private Integer luggageAmountMin; // объем багажника
    private Integer luggageAmountMax;
    private Integer fuelCapacityMin; // объем бака
    private Integer fuelCapacityMax;
    private Long complectationPriceMin; // цена комплектации
    private Long complectationPriceMax;

    // комплектации, которые выбрал пользователь, для сравнения
    private List<Complectation> userComplectations = new ArrayList<>();

    private Integer milage; // пробег
    private Float fuel_price; // стоимость топлива
    private Float percent; // процентная ставка(необязательное)
    private Integer is_credit; // будет ли использоваться кредит
    private Integer min_time_credit; //минимальный срок кредитования в МЕСЯЦАХ!!!
    private Integer max_time_credit; //максимальный срок кредитования в МЕСЯЦАХ!!!
    private Integer time_credit; // срок кредитования
    private Long first_payment; // первый взнос
    private Integer driving_experience; // водительский стаж
    private Integer age; // возраст
    private Integer expense_min; // расход в месяц минимальный
    private Integer expense_max; // расход в месяц максимальный
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

    public List<Complectation> getUserComplectations() {
        return userComplectations;
    }

    public void setUserComplectations(List<Complectation> userComplectations) {
        this.userComplectations = userComplectations;
    }

    public List<String> getGearbox() {
        return gearbox;
    }

    public void setGearbox(List<String> gearbox) {
        this.gearbox = gearbox;
    }

    public List<String> getGearing() {
        return gearing;
    }

    public void setGearing(List<String> gearing) {
        this.gearing = gearing;
    }

    public Integer getMilage() {
        return milage;
    }

    public void setMilage(Integer milage) {
        this.milage = milage;
    }

    public Float getFuel_price() {
        return fuel_price;
    }

    public void setFuel_price(Float fuel_price) {
        this.fuel_price = fuel_price;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public Integer getMin_time_credit() {
        return min_time_credit;
    }

    public void setMin_time_credit(Integer min_time_credit) {
        this.min_time_credit = min_time_credit;
    }

    public Integer getMax_time_credit() {
        return max_time_credit;
    }

    public void setMax_time_credit(Integer max_time_credit) {
        this.max_time_credit = max_time_credit;
    }

    public Integer getTime_credit() {
        return time_credit;
    }

    public void setTime_credit(Integer time_credit) {
        this.time_credit = time_credit;
    }

    public Long getFirst_payment() {
        return first_payment;
    }

    public void setFirst_payment(Long first_payment) {
        this.first_payment = first_payment;
    }

    public Integer getDriving_experience() {
        return driving_experience;
    }

    public void setDriving_experience(Integer driving_experience) {
        this.driving_experience = driving_experience;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getExpense_min() {
        return expense_min;
    }

    public void setExpense_min(Integer expense_min) {
        this.expense_min = expense_min;
    }

    public Integer getExpense_max() {
        return expense_max;
    }

    public void setExpense_max(Integer expense_max) {
        this.expense_max = expense_max;
    }

    public Integer getIs_credit() {
        return (is_credit == null) ? 0 : is_credit;
    }

    public void setIs_credit(Integer is_credit) {
        this.is_credit = is_credit;
    }
}
