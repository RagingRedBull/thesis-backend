package com.thesis.backend.model.dto.sensor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class DhtSensorLogDto extends SensorLogDto{
    private float temperature;
    private float humidity;

    public DhtSensorLogDto(long id, SensorType type, SensorName name) {
        super(id,type, name);
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
