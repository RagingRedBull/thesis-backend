package com.thesis.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PostFireReportCompartmentDto {
    private Date timeDetected;
    private String compartmentName;
    private String floorDesc;
    private Float dht11;
    private Float dht22;
    private Float mq2;
    private Float mq5;
    private Float mq7;
    private Float mq135;
    private Float fire;
}
