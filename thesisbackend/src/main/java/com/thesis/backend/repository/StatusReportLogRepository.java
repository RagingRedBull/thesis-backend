package com.thesis.backend.repository;

import com.thesis.backend.model.entity.logs.StatusReportLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusReportLogRepository extends JpaRepository<StatusReportLog, Long> {
}
