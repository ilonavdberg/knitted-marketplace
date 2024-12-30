package com.knitted.marketplace.models.item;

import com.knitted.marketplace.exception.exceptions.InvalidEnumValueException;

public enum TargetGroup {
    FEMALES("females"),
    MALES("males"),
    UNISEX("unisex"),
    KIDS("kids"),
    BABIES("babies"),
    UNSPECIFIED("unspecified");

    private final String value;

    TargetGroup(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static TargetGroup fromString(String input) {
        if (input.isEmpty()) {
            return TargetGroup.UNSPECIFIED;
        }

        for (TargetGroup targetGroup : TargetGroup.values()) {
            if (targetGroup.value.equalsIgnoreCase(input)) {
                return targetGroup;
            }
        }

        throw new InvalidEnumValueException(input);
    }
}
