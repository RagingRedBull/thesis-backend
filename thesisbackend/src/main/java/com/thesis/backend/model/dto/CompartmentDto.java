package com.thesis.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class CompartmentDto {
    private int id;
    private String name;
    private int xDimension;
    private int yDimension;
    private int width;
    private int depth;
    private int xKonva;
    private int yKonva;
    private int widthKonva;
    private int heightKonva;
    private int floorId;
}
