package com.thesis.backend.model.dto.logs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@NoArgsConstructor
@Getter
@Setter
public class DetectorUnitLogDto {
    private long id;
    @JsonProperty(required = true)
    private String macAddress;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeRecorded;
    @JsonProperty(required = true)
    private Set<SensorLogDto> sensorLogSet;
}
