package com.jajteam.jajmeup.exception;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException() {
        super("This username is already used");
    }
}
