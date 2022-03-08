package com.thesis.backend.repository;

import com.thesis.backend.model.entity.MachineLearningOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MachineLearningOutputRepository extends JpaRepository<MachineLearningOutput, Long> {
    @Query(value = "SELECT * FROM `ml_output` ORDER BY `ml_output`.`id` DESC LIMIT 1", nativeQuery = true)
    MachineLearningOutput getLatestOutput();
}
