package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.PostFireReportLogDto;
import com.thesis.backend.model.entity.logs.PostFireReportLog;

public class PostFireReportLogMapper implements EntityMapper<PostFireReportLog, PostFireReportLogDto> {
    @Override
    public PostFireReportLogDto mapToDto(PostFireReportLog postFireReportLog) {
        PostFireReportLogDto dto = new PostFireReportLogDto();
        dto.setFireOut(postFireReportLog.getFireOut());
        dto.setTimeOccurred(postFireReportLog.getTimeOccurred());
        return dto;
    }

    @Override
    public PostFireReportLog mapToEntity(PostFireReportLogDto postFireReportLogDto) {
        PostFireReportLog entity = new PostFireReportLog();
        entity.setTimeOccurred(postFireReportLogDto.getTimeOccurred());
        return entity;
    }
}
