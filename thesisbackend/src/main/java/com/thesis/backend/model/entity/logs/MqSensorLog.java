package com.thesis.backend.model.entity.logs;

import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "MQ")
public class MqSensorLog extends SensorLog{
    @Column(name = "mq_value")
    private int mqValue;

    private MqSensorLog() {

    }

    @PersistenceConstructor
    public MqSensorLog(long id, SensorType type, SensorName name) {
        super(id, name, type);
    }

    public int getMqValue() {
        return mqValue;
    }

    public void setMqValue(int mqValue) {
        this.mqValue = mqValue;
    }
}
