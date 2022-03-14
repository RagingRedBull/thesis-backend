package com.thesis.backend.repository;

import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.entity.DetectorUnit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompartmentRepository extends JpaRepository<Compartment, Integer> {
    List<Compartment> findByFloorId(int floorId, Sort sort);

}
