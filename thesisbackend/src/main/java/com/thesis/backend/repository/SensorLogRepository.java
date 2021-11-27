package com.thesis.backend.repository;

import com.thesis.backend.model.entity.logs.SensorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorLogRepository extends JpaRepository<SensorLog, Long> {
    @Query(value = "SELECT * FROM sensor_log WHERE detector_unit_log_id = ?", nativeQuery = true)
    List<SensorLog> findByDetectorUnitLog(long id);
}
