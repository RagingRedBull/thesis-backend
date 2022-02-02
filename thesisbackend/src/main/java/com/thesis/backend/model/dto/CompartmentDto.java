package com.thesis.backend.model.dto;

import javax.persistence.Column;

public class CompartmentDto {
    private int id;
    private int xDimension;
    private int yDimension;
    private int width;
    private int depth;
    private int xKonva;
    private int yKonva;
    private int widthKonva;
    private int heightKonva;
    private int floorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getxDimension() {
        return xDimension;
    }

    public void setxDimension(int xDimension) {
        this.xDimension = xDimension;
    }

    public int getyDimension() {
        return yDimension;
    }

    public void setyDimension(int yDimension) {
        this.yDimension = yDimension;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getxKonva() {
        return xKonva;
    }

    public void setxKonva(int xKonva) {
        this.xKonva = xKonva;
    }

    public int getyKonva() {
        return yKonva;
    }

    public void setyKonva(int yKonva) {
        this.yKonva = yKonva;
    }

    public int getWidthKonva() {
        return widthKonva;
    }

    public void setWidthKonva(int widthKonva) {
        this.widthKonva = widthKonva;
    }

    public int getHeightKonva() {
        return heightKonva;
    }

    public void setHeightKonva(int heightKonva) {
        this.heightKonva = heightKonva;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }
}
