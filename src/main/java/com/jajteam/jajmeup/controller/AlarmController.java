package com.jajteam.jajmeup.controller;

import com.jajteam.jajmeup.command.AlarmCommand;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.exception.InvalidAlarmException;
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

    @Inject
    private AlarmService service;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity createAlarm(@RequestBody @Valid AlarmCommand command) throws InvalidAlarmException, EntityNotFoundException {
        service.create(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
