package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.CompartmentDto;
import com.thesis.backend.model.entity.Compartment;

public class CompartmentMapper implements EntityMapper<Compartment, CompartmentDto> {
    @Override
    public CompartmentDto mapToDto(Compartment compartment) {
        CompartmentDto dto = new CompartmentDto();
        dto.setId(compartment.getId());
        dto.setxDimension(compartment.getxDimension());
        dto.setyDimension(compartment.getxDimension());
        dto.setWidth(compartment.getWidth());
        dto.setDepth(compartment.getDepth());
        dto.setxKonva(compartment.getxKonva());
        dto.setyKonva(compartment.getyKonva());
        dto.setHeightKonva(compartment.getHeightKonva());
        dto.setWidthKonva(compartment.getWidthKonva());
        return dto;
    }

    @Override
    public Compartment mapToEntity(CompartmentDto compartmentDto) {
        Compartment entity = new Compartment();
        entity.setId(compartmentDto.getId());
        entity.setxDimension(compartmentDto.getxDimension());
        entity.setyDimension(compartmentDto.getyDimension());
        entity.setWidth(compartmentDto.getWidth());
        entity.setDepth(compartmentDto.getDepth());
        entity.setxKonva(compartmentDto.getxKonva());
        entity.setyKonva(compartmentDto.getyKonva());
        entity.setHeightKonva(compartmentDto.getHeightKonva());
        entity.setWidthKonva(compartmentDto.getWidthKonva());
        return entity;
    }
}
