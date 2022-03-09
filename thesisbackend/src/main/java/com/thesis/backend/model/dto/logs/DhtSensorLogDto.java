package com.thesis.backend.model.dto.logs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Getter
@Setter
public class DhtSensorLogDto extends SensorLogDto{
    private float temperature;
    private float humidity;

    public DhtSensorLogDto(long id, SensorType type, SensorName name) {
        super(id,type, name);
    }
}
