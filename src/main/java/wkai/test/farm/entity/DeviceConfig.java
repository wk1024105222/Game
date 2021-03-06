package wkai.test.farm.entity;

import java.math.BigDecimal;

public class DeviceConfig {
    private String deviceid;

    private String isnotify;

    private BigDecimal airHumidityLow;

    private BigDecimal airHumidityHigh;

    private BigDecimal airTemperatureLow;

    private BigDecimal airTemperatureHigh;

    private BigDecimal soilTemperatureLow;

    private BigDecimal soilTemperatureHigh;

    private BigDecimal soilMoistureLow;

    private BigDecimal soilMoistureHigh;

    private String owner;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    public String getIsnotify() {
        return isnotify;
    }

    public void setIsnotify(String isnotify) {
        this.isnotify = isnotify == null ? null : isnotify.trim();
    }

    public BigDecimal getAirHumidityLow() {
        return airHumidityLow;
    }

    public void setAirHumidityLow(BigDecimal airHumidityLow) {
        this.airHumidityLow = airHumidityLow;
    }

    public BigDecimal getAirHumidityHigh() {
        return airHumidityHigh;
    }

    public void setAirHumidityHigh(BigDecimal airHumidityHigh) {
        this.airHumidityHigh = airHumidityHigh;
    }

    public BigDecimal getAirTemperatureLow() {
        return airTemperatureLow;
    }

    public void setAirTemperatureLow(BigDecimal airTemperatureLow) {
        this.airTemperatureLow = airTemperatureLow;
    }

    public BigDecimal getAirTemperatureHigh() {
        return airTemperatureHigh;
    }

    public void setAirTemperatureHigh(BigDecimal airTemperatureHigh) {
        this.airTemperatureHigh = airTemperatureHigh;
    }

    public BigDecimal getSoilTemperatureLow() {
        return soilTemperatureLow;
    }

    public void setSoilTemperatureLow(BigDecimal soilTemperatureLow) {
        this.soilTemperatureLow = soilTemperatureLow;
    }

    public BigDecimal getSoilTemperatureHigh() {
        return soilTemperatureHigh;
    }

    public void setSoilTemperatureHigh(BigDecimal soilTemperatureHigh) {
        this.soilTemperatureHigh = soilTemperatureHigh;
    }

    public BigDecimal getSoilMoistureLow() {
        return soilMoistureLow;
    }

    public void setSoilMoistureLow(BigDecimal soilMoistureLow) {
        this.soilMoistureLow = soilMoistureLow;
    }

    public BigDecimal getSoilMoistureHigh() {
        return soilMoistureHigh;
    }

    public void setSoilMoistureHigh(BigDecimal soilMoistureHigh) {
        this.soilMoistureHigh = soilMoistureHigh;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}