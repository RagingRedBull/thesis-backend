package com.thesis.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusReportLogDto {
    private String macAddress;
    private List<SensorStatusDto> sensorStatusDtoList;
}
