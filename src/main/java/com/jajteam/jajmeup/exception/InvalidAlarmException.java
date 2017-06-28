package com.jajteam.jajmeup.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public class InvalidAlarmException extends Exception {

    private List<ObjectError> errors;

    public InvalidAlarmException(BindingResult result) {
        super("Alarm validation failed");
        errors = result.getAllErrors();
    }

    public List<ObjectError> getErrors() {
        return errors;
    }
}
