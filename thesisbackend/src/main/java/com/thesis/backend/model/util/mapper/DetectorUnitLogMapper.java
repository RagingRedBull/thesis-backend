package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.detector.DetectorUnitLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;

public class DetectorUnitLogMapper implements Mapper<DetectorUnitLog, DetectorUnitLogDto> {

    public DetectorUnitLogMapper() {
    }


    @Override
    public DetectorUnitLogDto mapToDto(DetectorUnitLog entity) {
        DetectorUnitLogDto dto = new DetectorUnitLogDto();
        dto.setId(entity.getId());
        dto.setMacAddress(entity.getMacAddress());
        dto.setTimeRecorded(entity.getTimeRecorded());
        return dto;
    }

    @Override
    public DetectorUnitLog mapToEntity(DetectorUnitLogDto dto) {
        DetectorUnitLog entity = new DetectorUnitLog();
        entity.setId(dto.getId());
        entity.setMacAddress(dto.getMacAddress());
        entity.setTimeRecorded(dto.getTimeRecorded());
        return entity;
    }
}
