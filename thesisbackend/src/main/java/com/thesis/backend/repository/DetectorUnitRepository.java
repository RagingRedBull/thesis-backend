package com.thesis.backend.repository;

import com.thesis.backend.model.entity.DetectorUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectorUnitRepository extends JpaRepository<DetectorUnit, String> {

}
