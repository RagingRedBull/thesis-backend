package net.prmts.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.prmts.app.model.DetectorUnit;

@Repository
public interface DetectorUnitRepository extends JpaRepository<DetectorUnit, String> {

}
