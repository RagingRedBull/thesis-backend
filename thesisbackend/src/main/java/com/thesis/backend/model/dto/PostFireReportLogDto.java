package com.thesis.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.dto.logs.PostFireReportCompartmentDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostFireReportLogDto {
    private Long id;
    private LocalDateTime timeOccurred;
    private LocalDateTime fireOut;
    private Date dateOccurred;
    private List<PostFireReportCompartmentDto> affectedCompartments;
    public PostFireReportLogDto() {

    }

    public PostFireReportLogDto(Long id, Date dateOccurred) {
        this.id = id;
        this.dateOccurred = dateOccurred;
    }
}
