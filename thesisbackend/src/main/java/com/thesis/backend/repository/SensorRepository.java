package com.thesis.backend.repository;

import com.thesis.backend.model.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Set<Sensor> findByIdIn(List<Integer> idList);
}
