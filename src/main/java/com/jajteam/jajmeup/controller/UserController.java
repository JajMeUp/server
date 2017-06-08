package com.jajteam.jajmeup.controller;

import com.jajteam.jajmeup.command.UserCommand;
import com.jajteam.jajmeup.exception.InvalidCredentialException;
import com.jajteam.jajmeup.exception.UserAlreadyExistException;
import com.jajteam.jajmeup.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
public class UserController {

    @Inject
    private UserService service;

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity register(@RequestBody @Valid UserCommand command) throws UserAlreadyExistException {
        service.create(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity login(@RequestParam(name = "username") String username,
                                @RequestParam(name = "password") String password) throws InvalidCredentialException {
        return ResponseEntity.status(HttpStatus.OK).body(service.authenticate(username, password));
    }
}
