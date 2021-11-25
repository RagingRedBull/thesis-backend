package com.thesis.backend.model.dto.detector;

import com.thesis.backend.model.dto.sensor.SensorUpdateDto;

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
