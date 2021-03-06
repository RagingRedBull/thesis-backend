package com.thesis.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FloorDto {
    private int id;
    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private String description;
    @JsonProperty(required = true)
    private String imageUrl;
    @JsonProperty(required = true)
    private int order;
}
