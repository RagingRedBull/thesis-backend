package com.thesis.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineLearningOutputDto {
    private int xStart;
    private int yStart;
    private int floorStart;
    private int xEnd;
    private int yEnd;
    private int floorEnd;
}
