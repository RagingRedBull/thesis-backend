package com.thesis.backend.model.dto;

import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.Floor;

import java.util.List;

public class FloorDto {
    private int id;
    private String name;
    private String description;
    private List<DetectorUnit> associatedDetectorUnitList;

    public FloorDto(Floor entity){
        this.id = entity.getId();
        this.name = entity.getDescription();
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
