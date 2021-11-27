package com.thesis.backend.model.dto.sensor;

public class MqSensorLogDto extends SensorLogDto{
    private int mqValue;
    public MqSensorLogDto(String type, String name) {
        super(type, name);
    }

    public int getMqValue() {
        return mqValue;
    }

    public MqSensorLogDto setMqValue(int mqValue) {
        this.mqValue = mqValue;
        return this;
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
