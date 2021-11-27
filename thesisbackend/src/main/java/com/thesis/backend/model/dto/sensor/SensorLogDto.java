package com.thesis.backend.model.dto.sensor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = DhtSensorLogDto.class, name = "DHT"),
                @JsonSubTypes.Type(value = MqSensorLogDto.class, name = "MQ")
        }
)
public class SensorLogDto {
    private long id;
    private String type;
    private String name;

    public SensorLogDto() {
        // Default
    }

    public SensorLogDto(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public SensorLogDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public SensorLogDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public SensorLogDto setName(String name) {
        this.name = name;
        return this;
    }
}
