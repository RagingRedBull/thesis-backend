package com.thesis.backend.repository;

import com.thesis.backend.model.entity.logs.SensorStatusReportLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorStatusReportLogRepository extends JpaRepository<SensorStatusReportLog, Long> {
}
