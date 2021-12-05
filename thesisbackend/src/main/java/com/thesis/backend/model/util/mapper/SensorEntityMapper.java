package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.sensor.SensorDto;
import com.thesis.backend.model.entity.Sensor;

public class SensorEntityMapper implements EntityMapper<Sensor, SensorDto> {
    @Override
    public SensorDto mapToDto(Sensor entity) {
        SensorDto dto = new SensorDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    @Override
    public Sensor mapToEntity(SensorDto dto) {
        Sensor entity = new Sensor();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
