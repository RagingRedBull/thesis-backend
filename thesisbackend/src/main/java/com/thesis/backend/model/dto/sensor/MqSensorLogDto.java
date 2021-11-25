package com.thesis.backend.model.dto.sensor;

public class MqSensorLogDto extends SensorLogDto{
    private int ppmReading;
    public MqSensorLogDto(String type, String name) {
        super(type, name);
    }

    public int getPpmReading() {
        return ppmReading;
    }

    public void setPpmReading(int ppmReading) {
        this.ppmReading = ppmReading;
    }

    @Override
    public String toString() {
        return "MqSensorLogDto{" +
                "type=" + super.getType() +
                ", name=" + super.getName() +
                ", ppmReading=" + ppmReading +
                '}';
    }
}
