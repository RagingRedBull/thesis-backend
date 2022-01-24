package com.thesis.backend.model.dto.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.dto.detector.DetectorUnitDto;

import java.util.Set;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class DetectorUnitUpdateDto {
    private DetectorUnitDto detectorUnitDto;
    private Set<SensorUpdateDto> sensorUpdateDtoSet;

    public DetectorUnitUpdateDto(DetectorUnitDto detectorUnitDto, Set<SensorUpdateDto> sensorUpdateDtoSet) {
        this.detectorUnitDto = detectorUnitDto;
        this.sensorUpdateDtoSet = sensorUpdateDtoSet;
    }

    public DetectorUnitDto getDetectorUnitDto() {
        return detectorUnitDto;
    }

    public void setDetectorUnitDto(DetectorUnitDto detectorUnitDto) {
        this.detectorUnitDto = detectorUnitDto;
    }

    public Set<SensorUpdateDto> getSensorUpdateDtoSet() {
        return sensorUpdateDtoSet;
    }

    public void setSensorUpdateDtoSet(Set<SensorUpdateDto> sensorUpdateDtoSet) {
        this.sensorUpdateDtoSet = sensorUpdateDtoSet;
    }

    @Override
    public String toString() {
        return "DetectorUnitUpdateDto{" +
                "detectorUnitDto=" + detectorUnitDto +
                ", sensorUpdateDtoSet=" + sensorUpdateDtoSet +
                '}';
    }
}
