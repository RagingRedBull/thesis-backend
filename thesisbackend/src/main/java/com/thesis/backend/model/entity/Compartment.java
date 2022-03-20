package com.thesis.backend.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "compartment")
public class Compartment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "x_dimension")
    private float xDimension;
    @Column(name = "y_dimension")
    private float yDimension;
    @Column(name = "width")
    private float width;
    @Column(name = "depth")
    private float depth;
    @Column(name = "x_konva")
    private float xKonva;
    @Column(name = "y_konva")
    private float yKonva;
    @Column(name = "width_konva")
    private float widthKonva;
    @Column(name = "height_konva")
    private float heightKonva;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "compartment", cascade = CascadeType.ALL)
    private Set<DetectorUnit> detectorUnits;
}
