package com.thesis.backend.repository;

import com.thesis.backend.model.entity.logs.StatusReportLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StatusReportLogRepository extends JpaRepository<StatusReportLog, Long> {
    @Query(value = "SELECT srl FROM StatusReportLog srl " +
            "WHERE srl.dateStart >= :start " +
            "AND srl.dateEnd <= :end")
    Page<StatusReportLog> getAllStatusReportLogsByDay(@Param("start") LocalDateTime start,
                                                      @Param("end") LocalDateTime end, Pageable page);
}
