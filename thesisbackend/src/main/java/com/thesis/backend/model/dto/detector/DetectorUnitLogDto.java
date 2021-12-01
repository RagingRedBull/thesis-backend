package com.thesis.backend.model.dto.detector;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.dto.sensor.SensorLogDto;

import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class DetectorUnitLogDto {
    private long id;
    private String macAddress;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeRecorded;
    private Set<SensorLogDto> sensorLogSet;

    public DetectorUnitLogDto() {
        // Default Empty
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

    public Set<SensorLogDto> getSensorLogSet() {
        return sensorLogSet;
    }

    public void setSensorLogSet(Set<SensorLogDto> sensorLogSet) {
        this.sensorLogSet = sensorLogSet;
    }

    @Override
    public String toString() {
        return "DetectorUnitLogDto{" +
                "id=" + id +
                ", macAddress='" + macAddress + '\'' +
                ", timeRecorded=" + timeRecorded +
                ", sensorLogList=" + sensorLogSet +
                '}';
    }
}
