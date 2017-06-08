package com.jajteam.jajmeup.controller;

import com.jajteam.jajmeup.dto.mapper.SimpleErrorDtoMapper;
import com.jajteam.jajmeup.exception.InvalidCredentialException;
import com.jajteam.jajmeup.exception.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity invalidCredentionException(InvalidCredentialException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SimpleErrorDtoMapper.toDto(e));
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity userAlreadyExist(UserAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SimpleErrorDtoMapper.toDto(e));
    }
}
