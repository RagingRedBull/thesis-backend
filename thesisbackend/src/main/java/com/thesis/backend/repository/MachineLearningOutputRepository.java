package com.thesis.backend.repository;

import com.thesis.backend.model.entity.MachineLearningOutput;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineLearningOutputRepository extends JpaRepository<MachineLearningOutput, Long> {
}
