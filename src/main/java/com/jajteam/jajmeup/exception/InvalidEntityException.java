package com.jajteam.jajmeup.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class InvalidEntityException extends Exception {

    private String entityName;
    private List<ObjectError> errors;

    public InvalidEntityException(String entityName, List<ObjectError> errors) {
        super(String.format("%s validation failed", entityName));
        this.entityName = entityName;
        this.errors = errors;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }
}
