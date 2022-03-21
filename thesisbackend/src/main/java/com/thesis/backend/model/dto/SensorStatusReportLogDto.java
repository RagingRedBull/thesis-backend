package com.thesis.backend.model.dto;

import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorStatusReportLogDto {
    private SensorName sensorName;
    private SensorType  sensorType;
    private Double average;
    private Float min;
    private Float max;

    public SensorStatusReportLogDto(){};

    public SensorStatusReportLogDto(Double average, Float min, Float max) {
        this.average = average;
        this.min = min;
        this.max = max;
    }
}
