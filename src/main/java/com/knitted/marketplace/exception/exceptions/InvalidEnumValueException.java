package com.knitted.marketplace.exception.exceptions;

public class InvalidEnumValueException extends RuntimeException {
    public InvalidEnumValueException(String input) {
        super("Invalid input value: " + input);
    }
}
