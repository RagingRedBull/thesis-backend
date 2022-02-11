package com.thesis.backend.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "floors",
    indexes = {
        @Index(name = "floor_order", columnList = "order_position", unique = true)
    })
public class Floor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "description", length = 20)
    private String description;
    @Column(name = "image_name", unique = true)
    private String imageName;
    @Column(name = "order_position")
    private int order;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Compartment> compartments;
}
