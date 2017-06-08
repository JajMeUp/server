package com.jajteam.jajmeup.exception;

public class NoSuchUserException extends Exception {

    public NoSuchUserException(String username) {
        super(String.format("User %s does not exists", username));
    }
}
