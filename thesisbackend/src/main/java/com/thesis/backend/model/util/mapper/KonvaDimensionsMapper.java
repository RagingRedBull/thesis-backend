package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.KonvaDimensionsDto;
import com.thesis.backend.model.entity.KonvaDimensions;

public class KonvaDimensionsMapper implements EntityMapper<KonvaDimensions, KonvaDimensionsDto> {
    @Override
    public KonvaDimensionsDto mapToDto(KonvaDimensions konvaDimensions) {
        KonvaDimensionsDto dto = new KonvaDimensionsDto();
        dto.setX(konvaDimensions.getX());
        dto.setY(konvaDimensions.getY());
        dto.setHeight(konvaDimensions.getHeight());
        dto.setWidth(konvaDimensions.getWidth());
        return dto;
    }

    @Override
    public KonvaDimensions mapToEntity(KonvaDimensionsDto konvaDimensionsDto) {
        KonvaDimensions entity = new KonvaDimensions();
        entity.setX(konvaDimensionsDto.getX());
        entity.setY(konvaDimensionsDto.getY());
        entity.setHeight(konvaDimensionsDto.getHeight());
        entity.setWidth(konvaDimensionsDto.getWidth());
        return entity;
    }
}
