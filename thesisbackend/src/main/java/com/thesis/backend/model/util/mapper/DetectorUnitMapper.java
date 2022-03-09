package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.DetectorUnitDto;
import com.thesis.backend.model.entity.DetectorUnit;

public class DetectorUnitMapper implements EntityMapper<DetectorUnit, DetectorUnitDto> {

    public DetectorUnitMapper() {

    }

    @Override
    public DetectorUnitDto mapToDto(DetectorUnit entity) {
        DetectorUnitDto dto = new DetectorUnitDto();
        dto.setMacAddress(entity.getMacAddress());
        dto.setIpV4(entity.getIpV4());
        dto.setName(entity.getName());
        dto.setXpos(entity.getXpos());
        dto.setYpos(entity.getYpos());
        if(entity.getCompartment() == null) {
            dto.setCompartmentId(null);
        } else {
            dto.setCompartmentId(entity.getCompartment().getId());
        }
        return dto;
    }

    @Override
    public DetectorUnit mapToEntity(DetectorUnitDto dto) {
        DetectorUnit entity = new DetectorUnit();
        entity.setMacAddress(dto.getMacAddress());
        entity.setIpV4(dto.getIpV4());
        entity.setName(dto.getName());
        entity.setXpos(dto.getXpos());
        entity.setYpos(dto.getYpos());
        return entity;
    }
}
