package com.thesis.backend.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReportType {
    PFR("Post Fire Report"), SR("Status Report");

    private final String type;

    ReportType(String type) {
        this.type = type;
    }
    @JsonValue
    public String getType() {
        return type;
    }
}
