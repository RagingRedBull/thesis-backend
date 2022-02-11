package com.thesis.backend.model.entity.logs;


import com.thesis.backend.model.dto.logs.DetectorUnitLogDto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "detector_unit_log",
        indexes = @Index(name = "time_recorded_idx", columnList = ("time_recorded DESC"))
)
@Data
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
    private Set<SensorLog> sensorLogSet;

}
