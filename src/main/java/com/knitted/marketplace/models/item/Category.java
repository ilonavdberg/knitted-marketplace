package com.knitted.marketplace.models.item;

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
    )),

    ACCESSORIES(EnumSet.of(
            Subcategory.BAGS,
            Subcategory.PURSES,
            Subcategory.WALLETS,
            Subcategory.JEWELRY
    )),

    HOME(EnumSet.of(
            Subcategory.BLANKETS,
            Subcategory.PILLOWS,
            Subcategory.BASKETS,
            Subcategory.WALL_HANGINGS,
            Subcategory.HOME_DECOR,
            Subcategory.KITCHEN_ITEMS,
            Subcategory.COZIES
    )),

    TOYS(EnumSet.of(
            Subcategory.STUFFED_ANIMALS,
            Subcategory.DOLLS,
            Subcategory.TOY_CLOTHING,
            Subcategory.INTERACTIVE_TOYS,
            Subcategory.BABY_TOYS
    ));

    private final Set<Subcategory> subcategories;

    Category(EnumSet<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }
}
