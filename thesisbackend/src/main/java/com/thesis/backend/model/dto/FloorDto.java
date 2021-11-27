package com.thesis.backend.model.dto;

import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.Floor;

import java.util.Set;

public class FloorDto {
    private int id;
    private String name;
    private String description;
    private Set<DetectorUnit> associatedDetectorUnitSet;

    public FloorDto(Floor entity){
        this.id = entity.getId();
        this.name = entity.getDescription();
    }

    public int getId() {
        return id;
    }

    public FloorDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public FloorDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FloorDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<DetectorUnit> getAssociatedDetectorUnitSet() {
        return associatedDetectorUnitSet;
    }

    public FloorDto setAssociatedDetectorUnitSet(Set<DetectorUnit> associatedDetectorUnitSet) {
        this.associatedDetectorUnitSet = associatedDetectorUnitSet;
        return this;
    }
}
