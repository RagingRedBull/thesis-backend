package com.thesis.backend.repository;

import com.thesis.backend.model.dto.SensorStatusDto;
import com.thesis.backend.model.entity.logs.SensorLog;
import com.thesis.backend.model.enums.SensorName;
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
    @Query(value = "SELECT new com.thesis.backend.model.dto.SensorStatusDto(" +
            "AVG(slog.temperature),MIN(slog.temperature),MAX(slog.temperature))" +
            "FROM SensorLog slog, DetectorUnitLog dlog " +
            "WHERE dlog.macAddress= :macAddress " +
            "AND dlog.timeRecorded >= :start " +
            "AND dlog.timeRecorded <= :end " +
            "AND slog.name=:sensorName ")
    SensorStatusDto getTemperatureStatusByDayByMacAddressBySensorName(@Param("macAddress") String macAddress, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("sensorName") SensorName sensorName);
    @Query(value = "SELECT new com.thesis.backend.model.dto.SensorStatusDto(" +
            "AVG(slog.mqValue),MIN(slog.mqValue),MAX(slog.mqValue))" +
            "FROM SensorLog slog, DetectorUnitLog dlog " +
            "WHERE dlog.macAddress= :macAddress " +
            "AND dlog.timeRecorded >= :start " +
            "AND dlog.timeRecorded <= :end " +
            "AND slog.name=:sensorName ")
    SensorStatusDto getMqValueStatusByDayByMacAddressBySensorNameSensorStatusDto(@Param("macAddress") String macAddress, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("sensorName") SensorName sensorName);

}
