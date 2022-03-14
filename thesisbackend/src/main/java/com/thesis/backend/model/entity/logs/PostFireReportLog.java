package com.thesis.backend.model.entity.logs;

import com.thesis.backend.model.entity.Compartment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post_fire_report")
@Getter
@Setter
public class PostFireReportLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "time_occurred", unique = true)
    private LocalDateTime timeOccurred;
    @Column(name = "fire_out")
    private LocalDateTime fireOut;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "postFireReportLog", cascade = CascadeType.ALL)
    private List<SensorLog> logsDetected;
}
