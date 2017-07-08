package com.jajteam.jajmeup.controller;

import com.jajteam.jajmeup.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
