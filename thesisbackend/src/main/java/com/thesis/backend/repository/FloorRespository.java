package com.thesis.backend.repository;

import com.thesis.backend.model.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRespository extends JpaRepository<Floor, Integer> {
}
