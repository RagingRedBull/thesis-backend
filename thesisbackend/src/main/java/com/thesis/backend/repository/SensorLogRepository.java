package com.thesis.backend.repository;

import com.thesis.backend.model.dto.SensorStatusReportLogDto;
import com.thesis.backend.model.dto.logs.PostFireReportCompartmentDto;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.enums.SensorName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorLogRepository extends JpaRepository<SensorLog, Long> {
    @Query(value = "SELECT * FROM sensor_log WHERE detector_unit_log_id = ? ORDER BY TYPE, NAME DESC",
            nativeQuery = true)
    List<SensorLog> findByDetectorUnitLog(long id);
    @Query(value = "SELECT new com.thesis.backend.model.dto.SensorStatusReportLogDto(AVG(slog.temperature), MIN(slog.temperature), MAX(slog.temperature)) " +
            "FROM SensorLog slog, DetectorUnitLog dlog " +
            "WHERE dlog.macAddress= :macAddress " +
            "AND dlog.id = slog.detectorUnitLog.id " +
            "AND dlog.timeRecorded >= :start " +
            "AND dlog.timeRecorded <= :end " +
            "AND slog.name = :sensorName ")
    SensorStatusReportLogDto getDhtStatusReportBetweenDate(@Param("macAddress") String macAddress,
                                                           @Param("start") LocalDateTime start,
                                                           @Param("end") LocalDateTime end,
                                                           @Param("sensorName")SensorName sensorName);
    @Query(value = "SELECT new com.thesis.backend.model.dto.SensorStatusReportLogDto(AVG(slog.mqValue), MIN(slog.mqValue), MAX(slog.mqValue)) " +
            "FROM SensorLog slog, DetectorUnitLog dlog " +
            "WHERE dlog.macAddress= :macAddress " +
            "AND dlog.id = slog.detectorUnitLog.id " +
            "AND dlog.timeRecorded >= :start " +
            "AND dlog.timeRecorded <= :end " +
            "AND slog.name = :sensorName ")
    SensorStatusReportLogDto getMqStatusReportBetweenDate(@Param("macAddress") String macAddress,
                                                          @Param("start") LocalDateTime start,
                                                          @Param("end") LocalDateTime end,
                                                          @Param("sensorName")SensorName sensorName);
    @Query(value = "SELECT new com.thesis.backend.model.dto.SensorStatusReportLogDto(AVG(slog.sensorValue), MIN(slog.sensorValue), MAX(slog.sensorValue)) " +
            "FROM SensorLog slog, DetectorUnitLog dlog " +
            "WHERE dlog.macAddress= :macAddress " +
            "AND dlog.id = slog.detectorUnitLog.id " +
            "AND dlog.timeRecorded >= :start " +
            "AND dlog.timeRecorded <= :end " +
            "AND slog.name = :sensorName ")
    SensorStatusReportLogDto getFireStatusReportBetweenDate(@Param("macAddress") String macAddress,
                                                            @Param("start") LocalDateTime start,
                                                            @Param("end") LocalDateTime end,
                                                            @Param("sensorName")SensorName sensorName);
    @Query(value = "SELECT new com.thesis.backend.model.dto.SensorStatusReportLogDto(AVG(slog.sound), MIN(slog.sound), MAX(slog.sound)) " +
            "FROM SensorLog slog, DetectorUnitLog dlog " +
            "WHERE dlog.macAddress= :macAddress " +
            "AND dlog.id = slog.detectorUnitLog.id " +
            "AND dlog.timeRecorded >= :start " +
            "AND dlog.timeRecorded <= :end " +
            "AND slog.name = :sensorName " )
    SensorStatusReportLogDto getSoundStatusReportBetweenDate(@Param("macAddress") String macAddress,
                                                             @Param("start") LocalDateTime start,
                                                             @Param("end") LocalDateTime end,
                                                             @Param("sensorName")SensorName sensorName);
    @Query(value = "SELECT new com.thesis.backend.model.dto.logs.PostFireReportCompartmentDto(" +
            "FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(dlog.timeRecorded)/300)*300)," +
            "comp.name," +
            "floor.description," +
            "MAX(CASE WHEN slog.name='DHT-11' then slog.temperature ELSE NULL END), " +
            "MAX(CASE WHEN slog.name='DHT-22' then slog.temperature ELSE NULL END), " +
            "MAX(CASE WHEN slog.name='MQ-2' then slog.mqValue ELSE NULL END), " +
            "MAX(CASE WHEN slog.name='MQ-5' then slog.mqValue ELSE NULL END), " +
            "MAX(CASE WHEN slog.name='MQ-7' then slog.mqValue ELSE NULL END), " +
            "MAX(CASE WHEN slog.name='MQ-135' then slog.mqValue ELSE NULL END), " +
            "MAX(CASE WHEN slog.name='FIRE' then slog.sensorValue ELSE NULL END)) " +
            "FROM SensorLog slog, DetectorUnit du " +
            "INNER JOIN DetectorUnitLog dlog ON dlog.id=slog.detectorUnitLog.id " +
            "INNER JOIN Compartment comp ON du.compartment.id=comp.id " +
            "INNER JOIN Floor floor ON comp.floor.id=floor.id " +
            "WHERE slog.postFireReportLog.id=:pfrId " +
            "GROUP BY FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(dlog.timeRecorded)/300)*300) " +
            "ORDER BY FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(dlog.timeRecorded)/300)*300) ASC")
    Page<PostFireReportCompartmentDto> getAffectedCompartmentsByPfrId(@Param("pfrId")Long pfrId, Pageable pageable);
}
