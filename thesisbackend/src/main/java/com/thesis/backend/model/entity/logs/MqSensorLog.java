package com.thesis.backend.model.entity.logs;

import com.thesis.backend.model.dto.sensor.MqSensorLogDto;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "MQ")
public class MqSensorLog extends SensorLog{
    @Column(name = "mq_value")
    private int mqValue;

    public MqSensorLog() {

    }

    public MqSensorLog(MqSensorLogDto dto) {
        super.setType(dto.getType());
        super.setName(dto.getName());
        this.mqValue = dto.getMqValue();
    }

    public int getMqValue() {
        return mqValue;
    }

    public void setMqValue(int mqValue) {
        this.mqValue = mqValue;
    }
}
