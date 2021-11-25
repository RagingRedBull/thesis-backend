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

    public void setId(long id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public LocalDateTime getTimeRecorded() {
        return timeRecorded;
    }

    public void setTimeRecorded(LocalDateTime timeRecorded) {
        this.timeRecorded = timeRecorded;
    }

    public List<SensorLogDto> getSensorLogList() {
        return sensorLogList;
    }

    public void setSensorLogList(List<SensorLogDto> sensorLogList) {
        this.sensorLogList = sensorLogList;
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
