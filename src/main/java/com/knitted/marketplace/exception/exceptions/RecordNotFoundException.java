package com.knitted.marketplace.exception.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(Long id) {
        super("The record with id " + id + " does not exist.");
    }
}
