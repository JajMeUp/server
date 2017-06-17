package com.jajteam.jajmeup.exception;

public class InvalidCredentialException extends Exception {

    public InvalidCredentialException() {
        super("Invalid username / password");
    }
}
