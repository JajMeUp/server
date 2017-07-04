package com.jajteam.jajmeup.controller;

import com.jajteam.jajmeup.domain.Friendship;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.exception.FriendshipAlreadyAnsweredException;
import com.jajteam.jajmeup.exception.InvalidEntityException;
import com.jajteam.jajmeup.exception.ProtectedResourceException;
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

    @RequestMapping(value = "/{targetId}", method = RequestMethod.POST)
    public ResponseEntity createFriendship(@PathVariable(name = "targetId") Long targetId) throws InvalidEntityException {
        friendshipService.create(targetId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/accept/{id}", method = RequestMethod.PUT)
    public ResponseEntity acceptFriendship(@PathVariable(name = "id") Long id) throws EntityNotFoundException, ProtectedResourceException, FriendshipAlreadyAnsweredException {
        friendshipService.answerFriendRequest(id, Friendship.ACCEPTED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/reject/{id}", method = RequestMethod.PUT)
    public ResponseEntity rejectFriendship(@PathVariable(name = "id") Long id) throws EntityNotFoundException, ProtectedResourceException, FriendshipAlreadyAnsweredException {
        friendshipService.answerFriendRequest(id, Friendship.REJECTED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
