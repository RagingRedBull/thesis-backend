package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.SensorStatusReportLogDto;
import com.thesis.backend.model.entity.logs.SensorStatusReportLog;
import com.thesis.backend.service.interfaces.EntityService;

public class SensorStatusReportMapper implements EntityMapper<SensorStatusReportLog, SensorStatusReportLogDto> {
    @Override
    public SensorStatusReportLogDto mapToDto(SensorStatusReportLog sensorStatusReportLog) {
        SensorStatusReportLogDto dto = new SensorStatusReportLogDto();
        dto.setSensorType(sensorStatusReportLog.getSensorType());
        dto.setSensorName(sensorStatusReportLog.getSensorName());
        dto.setAverage(sensorStatusReportLog.getAvg());
        dto.setMin(sensorStatusReportLog.getMin());
        dto.setMax(sensorStatusReportLog.getMax());
        return dto;
    }

    @Override
    public SensorStatusReportLog mapToEntity(SensorStatusReportLogDto sensorStatusReportLogDto) {
        SensorStatusReportLog statusReportLog = new SensorStatusReportLog();
        statusReportLog.setSensorType(sensorStatusReportLogDto.getSensorType());
        statusReportLog.setSensorName(sensorStatusReportLogDto.getSensorName());
        statusReportLog.setAvg(sensorStatusReportLogDto.getAverage());
        statusReportLog.setMin(sensorStatusReportLogDto.getMin());
        statusReportLog.setMax(sensorStatusReportLogDto.getMax());
        return statusReportLog;
    }
}
