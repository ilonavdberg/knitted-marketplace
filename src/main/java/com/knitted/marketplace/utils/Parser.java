package com.knitted.marketplace.utils;

import com.knitted.marketplace.models.item.ClothingSize;

import java.util.Arrays;
import java.util.List;

public class Parser {
    public static Double toDouble(String stringInput) throws NumberFormatException {
        return Double.valueOf(stringInput);
    }

    public static List<ClothingSize> toSizeList(String sizes) {
        if (sizes.isEmpty()) {
            return List.of(); // Return an empty list if the input is empty
        }
        return Arrays.stream(sizes.split(","))
                .map(ClothingSize::fromString)
                .toList();
    }
}
