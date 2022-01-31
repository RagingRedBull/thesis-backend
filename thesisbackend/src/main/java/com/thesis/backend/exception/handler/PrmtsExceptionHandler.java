package com.thesis.backend.exception.handler;

import com.thesis.backend.exception.InvalidFileTypeException;
import com.thesis.backend.exception.response.ApiErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class PrmtsExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
        apiErrorResponse.setMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException entityNotFoundException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND);
        apiErrorResponse.setMessage(entityNotFoundException.getLocalizedMessage());
        return buildResponseEntity(apiErrorResponse);
    }
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
