package net.prmts.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.prmts.app.model.DetectorUnitLog;

@Repository
public interface DetectorUnitLogRepository extends JpaRepository<DetectorUnitLog, Integer> {

}
