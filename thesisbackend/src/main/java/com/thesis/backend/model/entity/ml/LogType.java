package com.thesis.backend.model.entity.ml;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LogType {
    SMOKE("SMOKE"), HEAT("HEAT");
    private final String type;
}
