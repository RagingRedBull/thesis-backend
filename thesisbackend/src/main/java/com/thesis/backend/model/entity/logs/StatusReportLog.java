package com.thesis.backend.model.entity.logs;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "status_report_log")
@Getter
@Setter
public class StatusReportLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "mac_address")
    private String macAddress;
    @Column(name = "date_start")
    private LocalDateTime dateStart;
    @Column(name = "date_end")
    private LocalDateTime dateEnd;
    @OneToMany(mappedBy = "statusReportLog", cascade = {CascadeType.ALL})
    private List<SensorStatusReportLog> sensorStatusReportLogs;
}
