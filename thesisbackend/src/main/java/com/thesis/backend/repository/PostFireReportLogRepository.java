package com.thesis.backend.repository;

import com.thesis.backend.model.dto.PostFireReportLogDto;
import com.thesis.backend.model.entity.logs.PostFireReportLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostFireReportLogRepository extends JpaRepository<PostFireReportLog, Long> {
    @Query(value = "SELECT new com.thesis.backend.model.dto.PostFireReportLogDto(pfr.id,pfr.timeOccurred,pfr.fireOut) FROM PostFireReportLog pfr " +
            " WHERE pfr.fireOut IS NOT NULL")
    List<PostFireReportLogDto> getIdAndDates();
    @Query(value = "SELECT pfr.id FROM post_fire_report pfr " +
            "WHERE pfr.fire_out IS NOT NULL " +
            "ORDER BY pfr.time_occurred DESC " +
            "LIMIT 1", nativeQuery = true)
    Long getIdOfLatestPfrWithNoFireOut();
}
