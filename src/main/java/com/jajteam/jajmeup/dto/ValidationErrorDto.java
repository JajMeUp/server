package com.jajteam.jajmeup.dto;

import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationErrorDto {
    private String defaultMessage;
    private String entityName;
    private List<ObjectError> errors;

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
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
