package com.thesis.backend.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SensorName {
    DHT11("DHT-11"), DHT22("DHT-22"), MQ2("MQ-2"), MQ5("MQ-5"), MQ7("MQ-7"),
    MQ135("MQ-135"), FLAME_SENSOR("FIRE"), SOUND_SENSOR("SOUND");

    private final String name;

    SensorName(String name) {
        this.name = name;
    }
    @JsonValue
    public String getName() {
        return name;
    }
}
