package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.MachineLearningOutputDto;
import com.thesis.backend.model.entity.ml.LogType;
import com.thesis.backend.model.entity.ml.MachineLearningOutput;

public class MachineLearningOutputMapper implements EntityMapper<MachineLearningOutput, MachineLearningOutputDto> {
    @Override
    public MachineLearningOutputDto mapToDto(MachineLearningOutput machineLearningOutput) {
        MachineLearningOutputDto machineLearningOutputDto = new MachineLearningOutputDto();
        machineLearningOutputDto.setXStart(machineLearningOutput.getXStart());
        machineLearningOutputDto.setXEnd(machineLearningOutput.getXEnd());
        machineLearningOutputDto.setYStart(machineLearningOutput.getYStart());
        machineLearningOutputDto.setYEnd(machineLearningOutput.getYEnd());
        machineLearningOutputDto.setFloorStart(machineLearningOutput.getFloorStart());
        machineLearningOutputDto.setFloorEnd(machineLearningOutput.getFloorEnd());
        machineLearningOutputDto.setType(machineLearningOutput.getLogType());
        return machineLearningOutputDto;
    }

    @Override
    public MachineLearningOutput mapToEntity(MachineLearningOutputDto machineLearningOutputDto) {
        MachineLearningOutput machineLearningOutput = new MachineLearningOutput();
        machineLearningOutput.setXStart(machineLearningOutputDto.getXStart());
        machineLearningOutput.setXEnd(machineLearningOutputDto.getXEnd());
        machineLearningOutput.setYStart(machineLearningOutputDto.getYStart());
        machineLearningOutput.setYEnd(machineLearningOutputDto.getYEnd());
        machineLearningOutput.setFloorStart(machineLearningOutputDto.getFloorStart());
        machineLearningOutput.setFloorEnd(machineLearningOutputDto.getFloorEnd());
        machineLearningOutput.setLogType(machineLearningOutputDto.getType());
        return machineLearningOutput;
    }
}
