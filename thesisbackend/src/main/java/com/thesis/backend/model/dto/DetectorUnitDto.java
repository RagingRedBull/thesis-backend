package com.thesis.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.ALWAYS)
public class DetectorUnitDto {
    @JsonProperty(required = true)
    private String macAddress;
    @JsonProperty(required = true)
    private String ipV4;
    private String name;
    private int xpos;
    private int ypos;
    private Integer compartmentId;

    public DetectorUnitDto(String macAddress, String ipV4) {
        this.macAddress = macAddress;
        this.ipV4 = ipV4;
    }
}
