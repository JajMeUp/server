package com.jajteam.jajmeup.dto.mapper;

import com.jajteam.jajmeup.dto.SimpleErrorDto;

public class SimpleErrorDtoMapper {

    public static SimpleErrorDto toDto(Exception e) {
        SimpleErrorDto dto = new SimpleErrorDto();
        dto.setDefaultMessage(e.getMessage());
        dto.setOrigin(e.getClass().getSimpleName());

        return dto;
    }
}
