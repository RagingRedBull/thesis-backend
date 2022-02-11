package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.CompartmentDto;
import com.thesis.backend.model.entity.Compartment;

public class CompartmentMapper implements EntityMapper<Compartment, CompartmentDto> {
    @Override
    public CompartmentDto mapToDto(Compartment compartment) {
        CompartmentDto dto = new CompartmentDto();
        dto.setId(compartment.getId());
        dto.setName(compartment.getName());
        dto.setXDimension(compartment.getXDimension());
        dto.setYDimension(compartment.getYDimension());
        dto.setWidth(compartment.getWidth());
        dto.setDepth(compartment.getDepth());
        dto.setXKonva(compartment.getXKonva());
        dto.setYKonva(compartment.getYKonva());
        dto.setHeightKonva(compartment.getHeightKonva());
        dto.setWidthKonva(compartment.getWidthKonva());
        return dto;
    }

    @Override
    public Compartment mapToEntity(CompartmentDto compartmentDto) {
        Compartment entity = new Compartment();
        entity.setId(compartmentDto.getId());
        entity.setName(compartmentDto.getName());
        entity.setXDimension(compartmentDto.getXDimension());
        entity.setYDimension(compartmentDto.getYDimension());
        entity.setWidth(compartmentDto.getWidth());
        entity.setDepth(compartmentDto.getDepth());
        entity.setXKonva(compartmentDto.getXKonva());
        entity.setYKonva(compartmentDto.getYKonva());
        entity.setHeightKonva(compartmentDto.getHeightKonva());
        entity.setWidthKonva(compartmentDto.getWidthKonva());
        return entity;
    }
}
