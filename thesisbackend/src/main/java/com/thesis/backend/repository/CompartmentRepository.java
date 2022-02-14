package com.thesis.backend.repository;

import com.thesis.backend.model.entity.Compartment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CompartmentRepository extends JpaRepository<Compartment, Integer> {
    Set<Compartment> findByFloorId(int floorId, Sort sort);
}
