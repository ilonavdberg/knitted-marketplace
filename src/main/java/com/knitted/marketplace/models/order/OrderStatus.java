package com.knitted.marketplace.models.order;

public enum OrderStatus {
    CLOSED("closed");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
