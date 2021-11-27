package com.thesis.backend.model.dto.update;


public class SensorUpdateDto {
    private int sensorId;
    private boolean toEnable;

    public SensorUpdateDto() {
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
                "\nsensorId=" + sensorId +
                ",\n toEnable=" + toEnable +
                '}';
    }
}
