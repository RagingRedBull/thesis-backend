package com.thesis.backend.exception;

public class PrmtsEntityNotFoundException extends RuntimeException{
    public PrmtsEntityNotFoundException(Class<?> clazz,int id) {
        super("No " + clazz.getSimpleName() + " with ID: " + id);
    }

    public PrmtsEntityNotFoundException(Class<?> clazz,String id) {
        super("No " + clazz.getSimpleName() + " with ID: " + id);
    }

    public PrmtsEntityNotFoundException(Class<?> clazz,long id) {
        super("No " + clazz.getSimpleName() + " with ID: " + id);
    }
}
