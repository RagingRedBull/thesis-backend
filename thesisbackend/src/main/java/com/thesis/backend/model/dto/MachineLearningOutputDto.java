package com.thesis.backend.model.dto;

import com.thesis.backend.model.entity.ml.LogType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineLearningOutputDto {
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private int floorStart;
    private int floorEnd;
    private LogType type;
}
