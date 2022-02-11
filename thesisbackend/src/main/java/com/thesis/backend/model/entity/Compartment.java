package com.thesis.backend.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Table(name = "compartment")
public class Compartment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "compartment")
    private Set<DetectorUnit> detectorUnits;
}
