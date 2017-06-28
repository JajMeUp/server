package com.jajteam.jajmeup.controller;

import com.jajteam.jajmeup.command.UserCommand;
import com.jajteam.jajmeup.exception.InvalidCredentialException;
import com.jajteam.jajmeup.exception.UserAlreadyExistException;
import com.jajteam.jajmeup.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
public class UserController {

    private UserService service;

    @Inject
    public UserController(UserService service) {
        this.service = service;
    }

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

    @RequestMapping(value = "/api/me", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity me() {
        return ResponseEntity.status(HttpStatus.OK).body(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
