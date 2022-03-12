package com.thesis.backend.model.entity.logs;

import com.thesis.backend.model.converter.SensorNameConverter;
import com.thesis.backend.model.converter.SensorTypeConverter;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sensor_status_log")
@Getter
@Setter
public class SensorStatusReportLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "sensor_name")
    @Convert(converter = SensorNameConverter.class)
    private SensorName sensorName;
    @Column(name = "sensor_type")
    @Convert(converter = SensorTypeConverter.class)
    private SensorType sensorType;
    @Column(name = "min")
    private Float min;
    @Column(name = "max")
    private Float max;
    @Column(name = "avg")
    private Double avg;
    @ManyToOne
    @JoinColumn(name = "status_report_log_id", nullable = false)
    private StatusReportLog statusReportLog;
}
