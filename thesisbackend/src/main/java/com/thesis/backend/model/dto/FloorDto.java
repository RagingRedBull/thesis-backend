package com.thesis.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.entity.Floor;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class FloorDto {
    private int id;
    private String name;
    private String description;

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

}
