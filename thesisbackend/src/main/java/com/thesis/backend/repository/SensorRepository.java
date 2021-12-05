package com.thesis.backend.repository;

import com.thesis.backend.model.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Set<Sensor> findByIdIn(List<Integer> idList);
}
