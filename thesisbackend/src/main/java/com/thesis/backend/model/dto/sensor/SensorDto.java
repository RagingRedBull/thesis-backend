package com.thesis.backend.model.dto.sensor;

import com.thesis.backend.model.entity.Sensor;

public class SensorDto {
    private int id;
    private String name;
    private String type;
    private String description;

    public SensorDto() {
    }

    public SensorDto(Sensor entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.type = entity.getType();
        this.description = entity.getDescription();
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
}
