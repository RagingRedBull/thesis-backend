package com.thesis.backend.model.dto.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class SensorUpdateDto {
    private int sensorId;
    private boolean toEnable;

    public SensorUpdateDto(int sensorId, boolean toEnable) {
        this.sensorId = sensorId;
        this.toEnable = toEnable;
    }
}
