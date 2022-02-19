package com.thesis.backend.model.dto.logs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.enums.SensorName;
import com.thesis.backend.model.enums.SensorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Getter
@Setter
@NoArgsConstructor
public class SensorDto {
    private int id;
    private SensorName name;
    private SensorType type;
    private String description;

}
