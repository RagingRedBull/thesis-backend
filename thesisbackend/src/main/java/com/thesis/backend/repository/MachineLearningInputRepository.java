package com.thesis.backend.repository;

import com.thesis.backend.model.entity.ml.MachineLearningInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MachineLearningInputRepository extends JpaRepository<MachineLearningInput, Long> {
    @Query(value = "SELECT * FROM `ml_input` ORDER BY `ml_input`.`time_recorded` DESC LIMIT 1",
            nativeQuery = true)
    MachineLearningInput getLatestInput();
}
