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

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
