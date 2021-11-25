package com.thesis.backend.model.entity.logs;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",
        discriminatorType = DiscriminatorType.STRING)
@Table(name = "sensor_log")
public class SensorLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    protected long id;
    @Column(name = "name", length = 15)
    protected String name;
    @Column(name = "type", insertable = false, updatable = false)
    protected String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detector_unit_log_id")
    private DetectorUnitLog detectorUnitLog;

    public SensorLog() {
        // Default Empty
    }

    public SensorLog(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public SensorLog(String name, String type, DetectorUnitLog detectorUnitLog) {
        this.name = name;
        this.type = type;
        this.detectorUnitLog = detectorUnitLog;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DetectorUnitLog getDetectorUnitLog() {
        return detectorUnitLog;
    }

    public void setDetectorUnitLog(DetectorUnitLog detectorUnitLog) {
        this.detectorUnitLog = detectorUnitLog;
    }
}
