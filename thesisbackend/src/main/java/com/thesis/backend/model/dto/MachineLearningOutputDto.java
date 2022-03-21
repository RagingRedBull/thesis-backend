package com.thesis.backend.model.dto;

import com.thesis.backend.model.entity.ml.LogType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineLearningOutputDto {
    private float xStart;
    private float yStart;
    private float xEnd;
    private float yEnd;
    private int floorStart;
    private int floorEnd;
    private LogType type;
}
