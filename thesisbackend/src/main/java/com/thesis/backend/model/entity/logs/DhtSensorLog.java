package com.thesis.backend.model.entity.logs;

import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "DHT")
public class DhtSensorLog extends SensorLog {
    @Column(name = "temperature")
    private float temperature;
    @Column(name = "humidity")
    private float humidity;

    private DhtSensorLog() {

    }

    @PersistenceConstructor
    public DhtSensorLog(long id, SensorType type, SensorName name) {
        super(id, name, type);
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
