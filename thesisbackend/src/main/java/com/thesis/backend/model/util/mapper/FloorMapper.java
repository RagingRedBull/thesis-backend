package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.FloorDto;
import com.thesis.backend.model.entity.Floor;

public class FloorMapper implements EntityMapper<Floor, FloorDto> {

    @Override
    public FloorDto mapToDto(Floor entity) {
        FloorDto dto = new FloorDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setImageUrl(entity.getImageName());
        return dto;
    }

    @Override
    public Floor mapToEntity(FloorDto dto) {
        Floor entity = new Floor();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImageName(dto.getImageUrl());
        return entity;
    }
}
