package com.thesis.backend.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SensorType {
    DHT("DHT"), MQ("MQ"), SOUND("SOUND"), FLAME("FLAME");
    private final String type;

    SensorType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }
}
