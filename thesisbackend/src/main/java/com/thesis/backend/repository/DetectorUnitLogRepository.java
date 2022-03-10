package com.thesis.backend.repository;

import com.thesis.backend.model.entity.logs.DetectorUnitLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DetectorUnitLogRepository extends JpaRepository<DetectorUnitLog, Long> {
    Set<DetectorUnitLog> findFirstByMacAddressIn(@Param("addresses") Set<String> addresses, Sort sort);
    @Query(value = "SELECT * FROM `detector_unit_log` " +
            "WHERE `detector_unit_log`.`mac_address` = ?1 " +
            "ORDER BY `detector_unit_log`.`time_recorded` DESC LIMIT 1",
    nativeQuery = true)
    DetectorUnitLog findLatestLog(String macAddress);
}
