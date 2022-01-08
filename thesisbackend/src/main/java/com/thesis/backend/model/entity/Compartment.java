package com.thesis.backend.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "compartment")
public class Compartment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private int id;
    @Column(name = "x_axis")
    private float x;
    @Column(name = "y_axis")
    private float y;
    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }
}
