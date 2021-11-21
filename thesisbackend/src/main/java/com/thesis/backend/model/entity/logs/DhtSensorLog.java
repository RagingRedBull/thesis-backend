package com.thesis.backend.model.entity.logs;

import com.thesis.backend.model.dto.DhtSensorLogDto;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "DHT")
public class DhtSensorLog extends SensorLog{
    @Column(name = "temperature")
    private float temperature;
    @Column(name = "humidity")
    private float humidity;

    private DhtSensorLog(){
        //default empty
    }

    public DhtSensorLog(DhtSensorLogDto dto, DetectorUnitLog detectorUnitLog) {
        super.setName(dto.getName());
        super.setType(dto.getType());
        super.setDetectorUnitLog(detectorUnitLog);
        this.temperature = dto.getTemperature();
        this.humidity = dto.getHumidity();
    }

    public DhtSensorLog(String name, String type, float temperature, float humidity) {
        super(name, type);
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public DhtSensorLog(String name, String type, DetectorUnitLog detectorUnitLog, float temperature, float humidity) {
        super(name, type, detectorUnitLog);
        this.temperature = temperature;
        this.humidity = humidity;
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
}
