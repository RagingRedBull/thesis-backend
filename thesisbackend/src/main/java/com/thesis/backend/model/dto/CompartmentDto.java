package com.thesis.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompartmentDto {
    private int id;
    private String name;
    private float xDimension;
    private float yDimension;
    private float width;
    private float depth;
    private float xKonva;
    private float yKonva;
    private float widthKonva;
    private float heightKonva;
    private float floorId;
}
