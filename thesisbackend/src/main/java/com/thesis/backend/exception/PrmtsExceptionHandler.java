package com.thesis.backend.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class PrmtsExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidFileTypeException.class)
    protected ResponseEntity<Object> handleInvalidFileType(InvalidFileTypeException invalidFileTypeException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY);
        apiErrorResponse.setMessage(invalidFileTypeException.getLocalizedMessage());
        return buildResponseEntity(apiErrorResponse);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
