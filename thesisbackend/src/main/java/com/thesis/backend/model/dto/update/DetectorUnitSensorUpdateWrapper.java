package com.thesis.backend.model.dto.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.dto.DetectorUnitDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class DetectorUnitSensorUpdateWrapper {
    private DetectorUnitDto detectorUnitDto;
    private Set<SensorUpdateDto> sensorUpdateDtoSet;

    public DetectorUnitSensorUpdateWrapper(DetectorUnitDto detectorUnitDto, Set<SensorUpdateDto> sensorUpdateDtoSet) {
        this.detectorUnitDto = detectorUnitDto;
        this.sensorUpdateDtoSet = sensorUpdateDtoSet;
    }
}
