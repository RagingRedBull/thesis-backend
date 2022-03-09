package com.thesis.backend.exception.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiError(String object, String message) {
        this.object = object;
        this.message = message;
    }

}
