package com.knitted.marketplace.exception.exceptions;

import java.util.Map;

public class ItemPublicationValidationException extends RuntimeException {
    public ItemPublicationValidationException(Long itemId, Map<String, String> validationErrors) {
        super(String.format("Item with ID '%s' failed publication validation with errors: %s", itemId, validationErrors));
    }
}
