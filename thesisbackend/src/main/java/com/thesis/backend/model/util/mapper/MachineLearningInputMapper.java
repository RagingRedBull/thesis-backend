package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.MachineLearningInputDto;
import com.thesis.backend.model.entity.MachineLearningInput;

public class MachineLearningInputMapper implements EntityMapper<MachineLearningInput, MachineLearningInputDto> {
    @Override
    public MachineLearningInputDto mapToDto(MachineLearningInput machineLearningInput) {
        MachineLearningInputDto dto = new MachineLearningInputDto();
        dto.setXOrigin(machineLearningInput.getXOrigin());
        dto.setYOrigin(machineLearningInput.getYOrigin());
        dto.setFloorOrigin(machineLearningInput.getFloorOrigin());
        dto.setTimeRecorded(machineLearningInput.getTimeRecorded());
        return dto;
    }

    @Override
    public MachineLearningInput mapToEntity(MachineLearningInputDto machineLearningInputDto) {
        MachineLearningInput entity = new MachineLearningInput();
        entity.setXOrigin(machineLearningInputDto.getXOrigin());
        entity.setYOrigin(machineLearningInputDto.getYOrigin());
        entity.setFloorOrigin(machineLearningInputDto.getFloorOrigin());
        entity.setTimeRecorded(machineLearningInputDto.getTimeRecorded());
        return entity;
    }
}
