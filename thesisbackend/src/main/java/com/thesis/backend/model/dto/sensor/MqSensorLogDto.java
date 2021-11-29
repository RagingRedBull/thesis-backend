package com.thesis.backend.model.dto.sensor;

import com.thesis.backend.model.entity.logs.SensorLog;

public class MqSensorLogDto extends SensorLogDto{
    private int mqValue;

    public MqSensorLogDto(SensorLog log) {
        super(log.getType(), log.getName());
    }

    public int getMqValue() {
        return mqValue;
    }

    public void setMqValue(int mqValue) {
        this.mqValue = mqValue;
    }

    @Override
    public String toString() {
        return "MqSensorLogDto{" +
                "type=" + super.getType() +
                ", name=" + super.getName() +
                ", mqValue=" + mqValue +
                '}';
    }
}
