package com.thesis.backend.model.dto.sensor;

public class SensorUpdateDto {
    private int sensorId;
    private boolean toEnable;

    public SensorUpdateDto(int sensorId, boolean toEnable) {
        this.sensorId = sensorId;
        this.toEnable = toEnable;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public boolean isToEnable() {
        return toEnable;
    }

    public void setToEnable(boolean toEnable) {
        this.toEnable = toEnable;
    }


    @Override
    public String toString() {
        return "SensorUpdateDto{" +
                "sensorId=" + sensorId +
                ", toEnable=" + toEnable +
                '}';
    }
}
