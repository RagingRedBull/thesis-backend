package com.thesis.backend.model.dto.sensor;

import com.thesis.backend.model.entity.Sensor;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;

public class SensorDto {
    private int id;
    private SensorName name;
    private SensorType type;
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

    public SensorName getName() {
        return name;
    }

    public void setName(SensorName name) {
        this.name = name;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
