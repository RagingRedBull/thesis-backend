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

    public SensorDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SensorDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public SensorDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SensorDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
