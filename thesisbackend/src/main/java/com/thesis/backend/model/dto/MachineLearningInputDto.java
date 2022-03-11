package com.thesis.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MachineLearningInputDto {
    private float xOrigin;
    private float yOrigin;
    private int floorOrigin;
    private LocalDateTime timeRecorded;
}
