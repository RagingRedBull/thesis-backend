package com.thesis.backend.model.dto.update;


public class SensorUpdateDto {
    private int sensorId;
    private boolean toEnable;

    public SensorUpdateDto() {
    }

    public int getSensorId() {
        return sensorId;
    }

    public SensorUpdateDto setSensorId(int sensorId) {
        this.sensorId = sensorId;
        return this;
    }

    public boolean isToEnable() {
        return toEnable;
    }

    public SensorUpdateDto setToEnable(boolean toEnable) {
        this.toEnable = toEnable;
        return this;
    }

    @Override
    public String toString() {
        return "SensorUpdateDto{" +
                "sensorId=" + sensorId +
                ", toEnable=" + toEnable +
                '}';
    }
}
