package com.thesis.backend.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "floors")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "description", length = 20)
    private String description;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "associatedFloor")
    private List<DetectorUnit> associatedDetectorUnitList;

    public Floor() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DetectorUnit> getAssociatedDetectorUnitList() {
        return associatedDetectorUnitList;
    }

    public void setAssociatedDetectorUnitList(List<DetectorUnit> associatedDetectorUnitList) {
        this.associatedDetectorUnitList = associatedDetectorUnitList;
    }
}
