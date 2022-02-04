package com.thesis.backend.repository;

import com.thesis.backend.model.entity.logs.SensorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorLogRepository extends JpaRepository<SensorLog, Long> {
    @Query(value = "SELECT * FROM sensor_log WHERE detector_unit_log_id = ? ORDER BY TYPE, NAME DESC", nativeQuery = true)
    List<SensorLog> findByDetectorUnitLog(long id);
}
