package com.knitted.marketplace.exception.exceptions;

public class InvalidStatusChangeException extends RuntimeException {
    public InvalidStatusChangeException(String oldStatus, String newStatus) {
        super("Cannot change status from \"" + oldStatus + "\" to \"" + newStatus + "\"");
    }
}
