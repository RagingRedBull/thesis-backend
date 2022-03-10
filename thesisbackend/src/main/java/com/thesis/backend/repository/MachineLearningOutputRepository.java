package com.thesis.backend.repository;

import com.thesis.backend.model.entity.ml.MachineLearningOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MachineLearningOutputRepository extends JpaRepository<MachineLearningOutput, Long> {
    @Query(value = "SELECT * FROM `ml_output` WHERE `ml_output`.`type` = ?1 ORDER BY `ml_output`.`id` DESC LIMIT 1", nativeQuery = true)
    MachineLearningOutput getLatestOutput(String type);
}
