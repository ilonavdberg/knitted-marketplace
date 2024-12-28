package com.knitted.marketplace.models.item;

import com.knitted.marketplace.exception.exceptions.InvalidEnumValueException;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum Category {
    CLOTHING(EnumSet.of(
            Subcategory.SWEATERS,
            Subcategory.CARDIGANS,
            Subcategory.TOPS,
            Subcategory.PANTS,
            Subcategory.SKIRTS,
            Subcategory.SOCKS,
            Subcategory.HATS_HEADBANDS,
            Subcategory.SCARVES,
            Subcategory.GLOVES
    ), "clothing"),

    ACCESSORIES(EnumSet.of(
            Subcategory.BAGS,
            Subcategory.PURSES,
            Subcategory.WALLETS,
            Subcategory.JEWELRY
    ), "accessories"),

    HOME(EnumSet.of(
            Subcategory.BLANKETS,
            Subcategory.PILLOWS,
            Subcategory.BASKETS,
            Subcategory.WALL_HANGINGS,
            Subcategory.HOME_DECOR,
            Subcategory.KITCHEN_ITEMS,
            Subcategory.COZIES
    ), "home"),

    TOYS(EnumSet.of(
            Subcategory.STUFFED_ANIMALS,
            Subcategory.DOLLS,
            Subcategory.TOY_CLOTHING,
            Subcategory.INTERACTIVE_TOYS,
            Subcategory.BABY_TOYS
    ), "toys"),

    UNSPECIFIED(EnumSet.noneOf(Subcategory.class), "unspecified");

    private final Set<Subcategory> subcategories;
    private final String value;

    Category(EnumSet<Subcategory> subcategories, String value) {
        this.subcategories = subcategories;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Category fromString(String input) {
        if (input.isEmpty()) {
            return Category.UNSPECIFIED;
        }

        for (Category category : Category.values()) {
            if (category.value.equalsIgnoreCase(input)) {
                return category;
            }
        }

        throw new InvalidEnumValueException(input);
    }
}
