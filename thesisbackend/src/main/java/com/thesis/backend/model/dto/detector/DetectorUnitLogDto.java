package com.thesis.backend.model.dto.detector;

import com.thesis.backend.model.dto.sensor.SensorLogDto;
import com.thesis.backend.model.entity.logs.DetectorUnitLog;

import java.time.LocalDateTime;
import java.util.List;

public class DetectorUnitLogDto {
    private long id;
    private String macAddress;
    private LocalDateTime timeRecorded;
    private List<SensorLogDto> sensorLogList;

    public DetectorUnitLogDto() {
        // Default Empty
    }

    public DetectorUnitLogDto(DetectorUnitLog entity) {
        this.id = entity.getId();
        this.macAddress = entity.getMacAddress();
        this.timeRecorded = entity.getTimeRecorded();
    }

    public long getId() {
        return id;
    }

    public DetectorUnitLogDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public DetectorUnitLogDto setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    public LocalDateTime getTimeRecorded() {
        return timeRecorded;
    }

    public DetectorUnitLogDto setTimeRecorded(LocalDateTime timeRecorded) {
        this.timeRecorded = timeRecorded;
        return this;
    }

    public List<SensorLogDto> getSensorLogList() {
        return sensorLogList;
    }

    public DetectorUnitLogDto setSensorLogList(List<SensorLogDto> sensorLogList) {
        this.sensorLogList = sensorLogList;
        return this;
    }

    @Override
    public String toString() {
        return "DetectorUnitLogDto{" +
                "id=" + id +
                ", macAddress='" + macAddress + '\'' +
                ", timeRecorded=" + timeRecorded +
                ", sensorLogList=" + sensorLogList +
                '}';
    }
}
