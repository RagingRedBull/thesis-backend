package com.thesis.backend.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "compartment")
public class Compartment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "x_dimension")
    private int xDimension;
    @Column(name = "y_dimension")
    private int yDimension;
    @Column(name = "width")
    private int width;
    @Column(name = "depth")
    private int depth;
    @Column(name = "x_konva")
    private int xKonva;
    @Column(name = "y_konva")
    private int yKonva;
    @Column(name = "width_konva")
    private int widthKonva;
    @Column(name = "height_konva")
    private int heightKonva;
    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;
    @OneToMany(mappedBy = "compartment")
    private Set<DetectorUnit> detectorUnits;

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

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Set<DetectorUnit> getDetectorUnits() {
        return detectorUnits;
    }

    public void setDetectorUnits(Set<DetectorUnit> detectorUnits) {
        this.detectorUnits = detectorUnits;
    }
}
