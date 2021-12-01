package com.thesis.backend.model.dto.update;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class DetectorUnitUpdateDto {
    private String macAddress;
    private Set<SensorUpdateDto> sensorUpdateDtoSet;

    public DetectorUnitUpdateDto(String macAddress, Set<SensorUpdateDto> sensorUpdateDtoList) {
        this.macAddress = macAddress;
        this.sensorUpdateDtoSet = sensorUpdateDtoList;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Set<SensorUpdateDto> getSensorUpdateDtoSet() {
        return sensorUpdateDtoSet;
    }

    public void setSensorUpdateDtoSet(Set<SensorUpdateDto> sensorUpdateDtoSet) {
        this.sensorUpdateDtoSet = sensorUpdateDtoSet;
    }
}
