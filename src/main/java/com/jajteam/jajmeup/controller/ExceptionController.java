package com.jajteam.jajmeup.controller;

import com.jajteam.jajmeup.dto.mapper.SimpleErrorDtoMapper;
import com.jajteam.jajmeup.dto.mapper.ValidationErrorDtoMapper;
import com.jajteam.jajmeup.exception.*;
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

    @ExceptionHandler({UserAlreadyExistException.class, FriendshipAlreadyAnsweredException.class})
    public ResponseEntity userAlreadyExist(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SimpleErrorDtoMapper.toDto(e));
    }

    @ExceptionHandler(ProtectedResourceException.class)
    public ResponseEntity protectedResourceException(ProtectedResourceException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(SimpleErrorDtoMapper.toDto(e));
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity invalidEntityException(InvalidEntityException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationErrorDtoMapper.toDto(e));
    }
}
