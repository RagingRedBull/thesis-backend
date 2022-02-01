package com.thesis.backend.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "konva_dimensions")
public class KonvaDimensions {
    @Id
    @Column(name = "compartment_id")
    private int id;
    @Column(name = "x_coord")
    private int x;
    @Column(name = "y_coord")
    private int y;
    @Column(name = "width")
    private int width;
    @Column(name = "height")
    private int height;
    @OneToOne
    @MapsId
    @JoinColumn(name = "compartment_id")
    private Compartment compartment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }
}
