package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.StatusReportLogDto;
import com.thesis.backend.model.entity.logs.StatusReportLog;

public class StatusReportLogMapper implements EntityMapper<StatusReportLog, StatusReportLogDto> {
    @Override
    public StatusReportLogDto mapToDto(StatusReportLog statusReportLog) {
        StatusReportLogDto dto = new StatusReportLogDto();
        dto.setMacAddress(statusReportLog.getMacAddress());
        dto.setStart(statusReportLog.getDateStart().toLocalTime());
        dto.setEnd(statusReportLog.getDateEnd().toLocalTime());
        return dto;
    }

    @Override
    public StatusReportLog mapToEntity(StatusReportLogDto statusReportLogDto) {
        StatusReportLog statusReportLog = new StatusReportLog();
        statusReportLog.setMacAddress(statusReportLogDto.getMacAddress());
        statusReportLog.setDateStart(statusReportLog.getDateStart());
        statusReportLog.setDateEnd(statusReportLog.getDateEnd());
        return statusReportLog;
    }
}
