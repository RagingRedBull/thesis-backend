package com.thesis.backend.repository;

import com.thesis.backend.model.entity.DetectorUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectorUnitRepository extends JpaRepository<DetectorUnit, String> {
    @Query(value = "SELECT * FROM `detector_unit`WHERE " +
            "`detector_unit`.`compartment_id` IN (SELECT `compartment`.`id` FROM `compartment` WHERE `compartment`.`floor_id`=?1);"
    , nativeQuery = true)
    List<DetectorUnit> getAllDetectorUnitsByFloorId(int floorId);
    @Query(value = "SELECT du.macAddress FROM DetectorUnit du")
    List<String> getAllDetectorUnitMacAddress();
}
