package com.thesis.backend.model.util.factory;

import com.thesis.backend.model.dto.update.SensorUpdateDto;
import com.thesis.backend.model.entity.Sensor;

public class SensorUpdateFactory {
    public SensorUpdateFactory () {
        // default empty
    }

    public SensorUpdateDto mapSensorUpdateToDto(Sensor sensor, boolean toEnable) {
        SensorUpdateDto dto = new SensorUpdateDto();
        dto.setSensorId(sensor.getId());
        dto.setToEnable(toEnable);
        return dto;
    }
}
