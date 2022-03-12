package com.thesis.backend.model.dto;

import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class SensorStatusDto {
    private SensorName sensorName;
    private SensorType  sensorType;
    private Double average;
    private Float min;
    private Float max;
    private LocalTime start;
    private LocalTime end;

    public SensorStatusDto(){};

    public SensorStatusDto(Double average, Float min, Float max) {
        this.average = average;
        this.min = min;
        this.max = max;
    }
}
