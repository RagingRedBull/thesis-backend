package com.thesis.backend.exception;

public class InvalidFileTypeException extends RuntimeException{

    public InvalidFileTypeException(String message) {
        super(message);
    }
}
