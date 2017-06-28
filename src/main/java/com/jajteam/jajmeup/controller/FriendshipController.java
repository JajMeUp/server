package com.jajteam.jajmeup.controller;

import com.jajteam.jajmeup.exception.InvalidEntityException;
import com.jajteam.jajmeup.service.FriendshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/friendship")
public class FriendshipController {

    private FriendshipService friendshipService;

    @Inject
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @RequestMapping(value = "/{requesterId}/{targetId}", method = RequestMethod.POST)
    public ResponseEntity createFriendship(@PathVariable(name = "requesterId") Long requesterId,
                                           @PathVariable(name = "targetId") Long targetId) throws InvalidEntityException {
        friendshipService.create(requesterId, targetId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
