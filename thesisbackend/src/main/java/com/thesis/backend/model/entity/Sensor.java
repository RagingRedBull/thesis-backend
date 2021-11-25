package com.thesis.backend.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "sensor")
public class Sensor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name", length = 30)
    private String name;
    @Column(name = "type", length = 50)
    private String type;
    @Column(name = "description", length = 255)
    private String description;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "associatedSensorSet")
    private Set<DetectorUnit> associatedDetectorUnitSet;

    private Sensor() {
        /// Default Empty
    }

    public Sensor(int id, String name, String type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<DetectorUnit> getAssociatedDetectorUnitSet() {
        return associatedDetectorUnitSet;
    }

    public void setAssociatedDetectorUnitSet(Set<DetectorUnit> associatedDetectorUnitSet) {
        this.associatedDetectorUnitSet = associatedDetectorUnitSet;
    }
}
