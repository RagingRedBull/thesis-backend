package com.thesis.backend.model.dto.sensor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class MqSensorLogDto extends SensorLogDto{
    private int mqValue;

    public MqSensorLogDto(long id, SensorType type, SensorName name) {
        super(id,type, name);
    }

    public MqSensorLogDto(SensorLog log) {
        super(log.getId(),log.getType(), log.getName());
    }

    public int getMqValue() {
        return mqValue;
    }

    public void setMqValue(int mqValue) {
        this.mqValue = mqValue;
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
