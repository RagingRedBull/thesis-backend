package com.thesis.backend.model.dto.sensor;

import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;

public class DhtSensorLogDto extends SensorLogDto{
    private float temperature;
    private float humidity;

    public DhtSensorLogDto(SensorType type, SensorName name) {
        super(type, name);
    }

    public DhtSensorLogDto(SensorLog entity) {
        super(entity.getType(), entity.getName());
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "DhtSensorLogDto{" +
                "type=" + super.getType() +
                ", name=" + super.getName() +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }
}
