package com.knitted.marketplace.utils.validation;

import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.item.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class ItemValidator {

    // Validation values
    private static final int TITLE_MIN_LENGTH = 6;
    private static final int TITLE_MAX_LENGTH = 30;
    private static final int DESCRIPTION_MIN_LENGTH = 30;
    private static final int DESCRIPTION_MAX_LENGTH = 300;
    private static final double PRICE_MIN_VALUE = 0.01; //price must be positive
    private static final int MIN_PHOTOS_REQUIRED = 1;

    public static ValidationResult validateForPublishing(Item item) {
        Map<String, String> errors = new HashMap<>();

        // add logic here;
        validateTitle(item).ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));
        validateDescription(item).ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));
        validatePrice(item).ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));
        validateCategory(item).ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));
        validateSubcategory(item).ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));
        validateClothingSize(item).ifPresent(entry -> errors.put(entry.getKey(), entry.getValue()));

        boolean isValid = errors.isEmpty();
        return new ValidationResult(isValid, errors);
    }

    private static Optional<SimpleEntry<String, String>> validateTitle(Item item) {
        String title = item.getTitle();

        if (title == null || title.length() < TITLE_MIN_LENGTH || title.length() > TITLE_MAX_LENGTH) {
            return Optional.of(new SimpleEntry<>("title", "Title must be between " + TITLE_MIN_LENGTH + " and " + TITLE_MAX_LENGTH + " characters."));
        }
        return Optional.empty();
    }

    private static Optional<SimpleEntry<String, String>> validateDescription(Item item) {
        String description = item.getDescription();

        if (description == null || description.length() < DESCRIPTION_MIN_LENGTH || description.length() > DESCRIPTION_MAX_LENGTH) {
            return Optional.of(new SimpleEntry<>("description", "Description must be between " + DESCRIPTION_MIN_LENGTH + " and " + DESCRIPTION_MAX_LENGTH + " characters."));
        }
        return Optional.empty();
    }

    private static Optional<SimpleEntry<String, String>> validatePrice(Item item) {
        Double price = item.getPrice();

        if (price == null || price < PRICE_MIN_VALUE) {
            return Optional.of(new SimpleEntry<>("price", "Price must be at least " + PRICE_MIN_VALUE + "."));
        }
        return Optional.empty();
    }

    private static Optional<SimpleEntry<String, String>> validateCategory(Item item) {
        Category category = item.getCategory();

        if (category == Category.UNSPECIFIED) {
            return Optional.of(new SimpleEntry<>("category", "Category must be specified."));
        }
        return Optional.empty();
    }

    private static Optional<SimpleEntry<String, String>> validateSubcategory(Item item) {
        Category category = item.getCategory();
        Subcategory subcategory = item.getSubcategory();

        if (subcategory == Subcategory.UNSPECIFIED) {
            return Optional.of(new SimpleEntry<>("subcategory", "Subcategory must be specified."));
        }

        if (!category.getSubcategories().contains(subcategory)) {
            return Optional.of(new SimpleEntry<>("subcategory", String.format("Invalid subcategory: '%s' is not a valid subcategory for the '%s' category.", subcategory, category)));
        }
        return Optional.empty();
    }

    private static Optional<SimpleEntry<String, String>> validateClothingSize(Item item) {
        Category category = item.getCategory();
        ClothingSize clothingSize = item.getClothingSize();

        if (category == Category.CLOTHING && clothingSize == ClothingSize.UNSPECIFIED) {
            return Optional.of(new SimpleEntry<>("clothing_size", "Clothing size must be specified."));
        }
        return Optional.empty();
    }

    private static Optional<SimpleEntry<String, String>> validateTargetGroup(Item item) {
        Category category = item.getCategory();
        TargetGroup targetGroup = item.getTargetgroup();

        if (category == Category.CLOTHING && targetGroup == TargetGroup.UNSPECIFIED) {
            return Optional.of(new SimpleEntry<>("target_group", "Target group must be specified for Clothing items."));
        }
        return Optional.empty();
    }

    private static Optional<SimpleEntry<String, String>> validatePhotos(Item item) {
        List<ImageFile> photos = item.getPhotos();

        if (photos.size() < MIN_PHOTOS_REQUIRED) {
            return Optional.of(new SimpleEntry<>("photos", String.format("You must upload at least %d photo%s.", MIN_PHOTOS_REQUIRED, MIN_PHOTOS_REQUIRED > 1 ? "s" : "")));
        }
        return Optional.empty();
    }
}
