package com.thesis.backend.model.entity.logs;


import com.thesis.backend.model.dto.DetectorUnitLogDto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "detector_unit_logs")
public class DetectorUnitLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "mac_address", length = 17, nullable = false)
    private String macAddress;
    @Column(name = "time_recorded", nullable = false)
    private LocalDateTime timeRecorded;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "detectorUnitLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SensorLog> sensorLogList;

    public DetectorUnitLog() {
        // Default Empty
    }

    public DetectorUnitLog(String macAddress, LocalDateTime timeRecorded, List<SensorLog> sensorLogList) {
        this.macAddress = macAddress;
        this.timeRecorded = timeRecorded;
        this.sensorLogList = sensorLogList;
    }

    public DetectorUnitLog(DetectorUnitLogDto dto) {
        this.id = dto.getId();
        this.macAddress = dto.getMacAddress();
        this.timeRecorded = dto.getTimeRecorded();
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

    public List<SensorLog> getSensorLogList() {
        return sensorLogList;
    }

    public void setSensorLogList(List<SensorLog> sensorLogList) {
        this.sensorLogList = sensorLogList;
    }

    @Override
    public String toString() {
        return "DetectorUnitLog{" +
                "id=" + id +
                ", macAddress='" + macAddress + '\'' +
                ", timeRecorded=" + timeRecorded +
                ", sensorLogList=" + sensorLogList +
                '}';
    }
}
