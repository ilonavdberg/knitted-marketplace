package com.knitted.marketplace.models.item;

import com.knitted.marketplace.exception.exceptions.InvalidEnumValueException;

public enum Subcategory {
    //Clothing
    SWEATERS("sweaters"),
    CARDIGANS("cardigans"),
    TOPS("tops"),
    PANTS("pants"),
    SKIRTS("skirts"),
    SOCKS("socks"),
    HATS_HEADBANDS("hats and headbands"),
    SCARVES("scarves"),
    GLOVES("gloves"),

    //Accessories
    BAGS("bags"),
    PURSES("purses"),
    WALLETS("wallets"),
    JEWELRY("jewelry"),

    //HOME
    BLANKETS("blankets"),
    PILLOWS("pillows"),
    BASKETS("baskets"),
    WALL_HANGINGS("wall hangings"),
    HOME_DECOR("home decor"),
    KITCHEN_ITEMS("kitchen items"),
    COZIES("cozies"),

    //TOYS
    STUFFED_ANIMALS("stuffed animals"),
    DOLLS("dolls"),
    TOY_CLOTHING("toy clothing"),
    INTERACTIVE_TOYS("interactive toys"),
    BABY_TOYS("baby toys"),

    UNSPECIFIED("unspecified");

    private final String value;

    Subcategory(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Subcategory fromString(String input) {
        if (input.isEmpty()) {
            return Subcategory.UNSPECIFIED;
        }

        for (Subcategory subcategory : Subcategory.values()) {
            if (subcategory.value.equalsIgnoreCase(input)) {
                return subcategory;
            }
        }

        throw new InvalidEnumValueException(input);
    }
}
