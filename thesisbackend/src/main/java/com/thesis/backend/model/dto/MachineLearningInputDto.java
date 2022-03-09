package com.thesis.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MachineLearningInputDto {
    private int xOrigin;
    private int yOrigin;
    private int floorOrigin;
    private LocalDateTime timeRecorded;
}
