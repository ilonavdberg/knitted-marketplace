package com.knitted.marketplace.models.item;

public enum ItemStatus {
    NOT_PUBLISHED("not published"),
    PUBLISHED("published"),
    SOLD("sold"),
    ARCHIVED("archived");

    private final String value;

    ItemStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
