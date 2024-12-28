package com.knitted.marketplace.models.item;

public enum TargetGroup {
    FEMALES("females"),
    MALES("males"),
    UNISEX("unisex"),
    KIDS("kids"),
    BABIES("babies");

    private final String value;

    TargetGroup(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
