package com.jajteam.jajmeup.dto.mapper;

import com.jajteam.jajmeup.dto.ValidationErrorDto;
import com.jajteam.jajmeup.exception.InvalidEntityException;

public class ValidationErrorDtoMapper {

    public static ValidationErrorDto toDto(InvalidEntityException e) {
        ValidationErrorDto dto = new ValidationErrorDto();
        dto.setDefaultMessage(e.getMessage());
        dto.setEntityName(e.getEntityName());
        dto.setErrors(e.getErrors());

        return dto;
    }
}
