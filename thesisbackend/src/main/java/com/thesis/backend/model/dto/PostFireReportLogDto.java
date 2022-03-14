package com.thesis.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.dto.logs.PostFireReportCompartmentDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostFireReportLogDto {
    private Long id;
    private LocalDateTime timeOccurred;
    private LocalDateTime fireOut;
    private List<PostFireReportCompartmentDto> affectedCompartments;
    public PostFireReportLogDto() {

    }

    public PostFireReportLogDto(Long id, LocalDateTime timeOccurred, LocalDateTime fireOut) {
        this.id = id;
        this.timeOccurred = timeOccurred;
        this.fireOut = fireOut;
    }
}
