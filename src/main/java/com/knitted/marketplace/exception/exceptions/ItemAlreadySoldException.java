package com.knitted.marketplace.exception.exceptions;

public class ItemAlreadySoldException extends RuntimeException {
    public ItemAlreadySoldException(String message) {
        super(message);
    }
}
