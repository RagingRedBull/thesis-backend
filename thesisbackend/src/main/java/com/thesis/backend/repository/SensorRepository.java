package com.thesis.backend.repository;

import com.thesis.backend.model.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    List<Sensor> findByIdIn(List<Integer> idList);
}
