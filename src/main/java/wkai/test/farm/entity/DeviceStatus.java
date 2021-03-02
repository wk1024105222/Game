package wkai.test.farm.entity;

import java.math.BigDecimal;
import java.util.Date;

public class DeviceStatus {
    private String deviceid;

    private BigDecimal airHumidity;

    private BigDecimal airTemperature;

    private BigDecimal soilTemperature;

    private BigDecimal soilMoisture;

    private BigDecimal electricQuantity;

    private Date currTime;

    public DeviceStatus(String deviceid, BigDecimal airHumidity, BigDecimal airTemperature, BigDecimal soilTemperature, BigDecimal soilMoisture, BigDecimal electricQuantity, Date currTime) {
        this.deviceid = deviceid;
        this.airHumidity = airHumidity;
        this.airTemperature = airTemperature;
        this.soilTemperature = soilTemperature;
        this.soilMoisture = soilMoisture;
        this.electricQuantity = electricQuantity;
        this.currTime = currTime;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    public BigDecimal getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(BigDecimal airHumidity) {
        this.airHumidity = airHumidity;
    }

    public BigDecimal getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(BigDecimal airTemperature) {
        this.airTemperature = airTemperature;
    }

    public BigDecimal getSoilTemperature() {
        return soilTemperature;
    }

    public void setSoilTemperature(BigDecimal soilTemperature) {
        this.soilTemperature = soilTemperature;
    }

    public BigDecimal getSoilMoisture() {
        return soilMoisture;
    }

    public void setSoilMoisture(BigDecimal soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public BigDecimal getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(BigDecimal electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public Date getCurrTime() {
        return currTime;
    }

    public void setCurrTime(Date currTime) {
        this.currTime = currTime;
    }
}