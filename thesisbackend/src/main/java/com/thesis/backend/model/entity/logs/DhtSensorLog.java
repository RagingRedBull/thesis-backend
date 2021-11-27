package com.thesis.backend.model.entity.logs;

import com.thesis.backend.model.dto.sensor.SensorLogDto;

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

    public DhtSensorLog(SensorLogDto dto, DetectorUnitLog detectorUnitLog) {
        super.setName(dto.getName());
        super.setType(dto.getType());
        super.setDetectorUnitLog(detectorUnitLog);
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
