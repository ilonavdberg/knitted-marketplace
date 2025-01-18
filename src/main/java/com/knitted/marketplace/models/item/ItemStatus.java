package com.knitted.marketplace.models.item;

import com.knitted.marketplace.exception.exceptions.InvalidEnumValueException;

public enum ItemStatus {
    DRAFT("draft"),
    PUBLISHED("published"),
    SOLD("sold"),
    ARCHIVED("archived"),

    UNSPECIFIED("unspecified");

    private final String value;

    ItemStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static ItemStatus fromString(String input) {
        if (input.isEmpty()) {
            return ItemStatus.UNSPECIFIED;
        }

        for (ItemStatus status : ItemStatus.values()) {
            if (status.value.equalsIgnoreCase(input)) {
                return status;
            }
        }

        throw new InvalidEnumValueException(input);
    }
}
