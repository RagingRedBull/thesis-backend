package com.thesis.backend.model.dto.update;

import java.util.Set;

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

    public DetectorUnitUpdateDto setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    public Set<SensorUpdateDto> getSensorUpdateDtoSet() {
        return sensorUpdateDtoSet;
    }

    public DetectorUnitUpdateDto setSensorUpdateDtoSet(Set<SensorUpdateDto> sensorUpdateDtoSet) {
        this.sensorUpdateDtoSet = sensorUpdateDtoSet;
        return this;
    }
}
