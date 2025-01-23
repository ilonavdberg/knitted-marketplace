package com.knitted.marketplace.exception.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("No user found with username: " + username);
    }
}
