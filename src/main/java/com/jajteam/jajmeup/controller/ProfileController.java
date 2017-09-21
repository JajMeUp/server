package com.jajteam.jajmeup.controller;

import com.jajteam.jajmeup.command.ProfileCommand;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.exception.InvalidEntityException;
import com.jajteam.jajmeup.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private ProfileService profileService;

    @Inject
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity searchProfileByName(@RequestParam(value = "q") String search) {
        return ResponseEntity.status(200).body(profileService.findProfileByNameSearch(search));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity update(@RequestBody ProfileCommand command) throws InvalidEntityException, EntityNotFoundException {
        profileService.update(command);
        return ResponseEntity.status(204).build();
    }
}
