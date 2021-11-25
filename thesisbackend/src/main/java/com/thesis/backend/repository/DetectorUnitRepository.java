package com.thesis.backend.repository;

import com.thesis.backend.model.entity.DetectorUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectorUnitRepository extends JpaRepository<DetectorUnit, String> {
}
