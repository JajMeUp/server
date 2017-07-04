package com.jajteam.jajmeup.exception;

public class FriendshipAlreadyAnsweredException extends Exception {
    public FriendshipAlreadyAnsweredException(Long id) {
        super(String.format("The friendship with id %d has already been answered", id));
    }
}
