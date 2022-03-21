package com.thesis.backend.exception;

public class CannotBuildPdfException extends RuntimeException{
    public CannotBuildPdfException(String message) {
        super(message);
    }
}
