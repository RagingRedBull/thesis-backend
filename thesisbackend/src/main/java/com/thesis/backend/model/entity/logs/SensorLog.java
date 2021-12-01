package com.thesis.backend.model.entity.logs;

import com.thesis.backend.model.converter.SensorNameConverter;
import com.thesis.backend.model.converter.SensorTypeConverter;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",
        discriminatorType = DiscriminatorType.STRING)
@Table(name = "sensor_log")
public abstract class SensorLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    protected long id;
    @Column(name = "name", length = 15)
    @Convert(converter = SensorNameConverter.class)
    protected SensorName name;
    @Column(name = "type", insertable = false, updatable = false)
    @Convert(converter = SensorTypeConverter.class)
    protected SensorType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detector_unit_log_id")
    private DetectorUnitLog detectorUnitLog;

    public SensorLog() {
        // Default Empty
    }

    public SensorLog(SensorName name, SensorType type) {
        this.name = name;
        this.type = type;
    }

    public SensorLog(SensorName name, SensorType type, DetectorUnitLog detectorUnitLog) {
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

    public SensorName getName() {
        return name;
    }

    public void setName(SensorName name) {
        this.name = name;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public DetectorUnitLog getDetectorUnitLog() {
        return detectorUnitLog;
    }

    public void setDetectorUnitLog(DetectorUnitLog detectorUnitLog) {
        this.detectorUnitLog = detectorUnitLog;
    }
}
