package com.thesis.backend.model.dto.logs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.thesis.backend.model.entity.logs.FireSensorLog;
import com.thesis.backend.model.entity.logs.SoundSensorLog;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = DhtSensorLogDto.class, name = "DHT"),
                @JsonSubTypes.Type(value = MqSensorLogDto.class, name = "MQ"),
                @JsonSubTypes.Type(value = FireSensorLog.class, name = "FIRE"),
                @JsonSubTypes.Type(value = SoundSensorLog.class, name = "SOUND")
        }
)
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Getter
@Setter
@NoArgsConstructor
public abstract class SensorLogDto {
    private long id;
    @JsonProperty(required = true)
    private SensorType type;
    @JsonProperty(required = true)
    private SensorName name;

    public SensorLogDto(long id, SensorType type, SensorName name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }
}
