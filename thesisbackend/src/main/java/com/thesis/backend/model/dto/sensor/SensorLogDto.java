package com.thesis.backend.model.dto.sensor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;

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
    private SensorType type;
    private SensorName name;

    public SensorLogDto() {
        // Default
    }

    public SensorLogDto(SensorType type, SensorName name) {
        this.type = type;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public SensorName getName() {
        return name;
    }

    public void setName(SensorName name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SensorLogDto{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name=" + name +
                '}';
    }
}
