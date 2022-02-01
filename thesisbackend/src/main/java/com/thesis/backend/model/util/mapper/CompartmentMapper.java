package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.CompartmentDto;
import com.thesis.backend.model.entity.Compartment;

public class CompartmentMapper implements EntityMapper<Compartment, CompartmentDto> {
    @Override
    public CompartmentDto mapToDto(Compartment compartment) {
        CompartmentDto dto = new CompartmentDto();
        dto.setxDimension(compartment.getxDimension());
        dto.setyDimension(compartment.getxDimension());
        dto.setWidth(compartment.getWidth());
        dto.setDepth(compartment.getDepth());
        return dto;
    }

    @Override
    public Compartment mapToEntity(CompartmentDto compartmentDto) {
        Compartment entity = new Compartment();
        entity.setxDimension(compartmentDto.getxDimension());
        entity.setyDimension(compartmentDto.getyDimension());
        entity.setWidth(compartmentDto.getWidth());
        entity.setDepth(compartmentDto.getDepth());
        return entity;
    }
}
