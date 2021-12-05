package com.thesis.backend.model.dto.update;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class SensorUpdateDto {
    private int sensorId;
    private boolean toEnable;

    private SensorUpdateDto() {
    }

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
                "\nsensorId=" + sensorId +
                ",\n toEnable=" + toEnable +
                '}';
    }
}
