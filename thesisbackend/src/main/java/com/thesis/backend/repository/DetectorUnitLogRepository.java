package com.thesis.backend.repository;

import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectorUnitLogRepository extends JpaRepository<DetectorUnitLog, Long> {
}
