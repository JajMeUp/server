package com.jajteam.jajmeup.controller;

import com.jajteam.jajmeup.command.AlarmCommand;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.exception.InvalidEntityException;
import com.jajteam.jajmeup.service.AlarmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

@RequestMapping("/api/alarm")
@RestController
public class AlarmController {

    private AlarmService service;

    @Inject
    public AlarmController(AlarmService service) {
        this.service = service;
    }

    @RequestMapping(value = "/create/{targetId, link, messageValue}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity createAlarm(@RequestBody @Valid AlarmCommand command) throws EntityNotFoundException, InvalidEntityException {
        service.create(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/lastalarm", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity lastAlarm(@RequestBody @Valid AlarmCommand command) throws EntityNotFoundException, InvalidEntityException {
        service.create(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
