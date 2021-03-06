package com.thesis.backend.exception.handler;

import com.thesis.backend.exception.InvalidFileException;
import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.exception.response.ApiErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;

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

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
        apiErrorResponse.setMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(PrmtsEntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(PrmtsEntityNotFoundException prmtsEntityNotFoundException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND);
        apiErrorResponse.setMessage(prmtsEntityNotFoundException.getLocalizedMessage());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(InvalidFileException.class)
    protected ResponseEntity<Object> handleInvalidFileType(InvalidFileException invalidFileTypeException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY);
        apiErrorResponse.setMessage(invalidFileTypeException.getLocalizedMessage());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException nullPointerException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        apiErrorResponse.setMessage(nullPointerException.getLocalizedMessage());
        return buildResponseEntity(apiErrorResponse);
    }
    @ExceptionHandler(FileNotFoundException.class)
    protected ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException fileNotFoundException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        apiErrorResponse.setMessage(fileNotFoundException.getLocalizedMessage());
        return buildResponseEntity(apiErrorResponse);
    }
    private ResponseEntity<Object> buildResponseEntity(ApiErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
