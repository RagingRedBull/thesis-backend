package com.thesis.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.dto.logs.SensorLogDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostFireReportLogDto {
    private Long id;
    private LocalDateTime timeOccurred;
    private LocalDateTime fireOut;
    private Date dateOccurred;
    private String compartmentName;
    private String floorDesc;
    private Set<SensorLogDto> sensorLogSet;

    public PostFireReportLogDto() {

    }

    public PostFireReportLogDto(Long id, Date dateOccurred) {
        this.id = id;
        this.dateOccurred = dateOccurred;
    }
}
