package com.thesis.backend.model.entity.logs;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "sensor_name")
    private SensorName sensorName;
    @Column(name = "sensor_type")
    private SensorType sensorType;
    @Column(name = "min")
    private float min;
    @Column(name = "max")
    private float max;
    @Column(name = "avg")
    private double avg;
    @ManyToOne
    @JoinColumn(name = "status_report_log_id", nullable = false)
    private StatusReportLog statusReportLog;
}
