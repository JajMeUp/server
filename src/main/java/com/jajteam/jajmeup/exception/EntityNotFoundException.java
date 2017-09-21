package com.jajteam.jajmeup.exception;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String className, String key) {
        super(String.format("No such %s with id %s", className, key));
    }
}
