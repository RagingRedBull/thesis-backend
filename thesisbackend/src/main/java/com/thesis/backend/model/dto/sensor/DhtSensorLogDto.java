package com.thesis.backend.model.dto.sensor;

public class DhtSensorLogDto extends SensorLogDto{
    private float temperature;
    private float humidity;

    public DhtSensorLogDto(String type, String name) {
        super(type, name);
    }

    public float getTemperature() {
        return temperature;
    }

    public DhtSensorLogDto setTemperature(float temperature) {
        this.temperature = temperature;
        return this;
    }

    public float getHumidity() {
        return humidity;
    }

    public DhtSensorLogDto setHumidity(float humidity) {
        this.humidity = humidity;
        return this;
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
