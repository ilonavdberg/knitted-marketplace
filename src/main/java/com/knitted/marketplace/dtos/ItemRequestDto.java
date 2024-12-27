package com.knitted.marketplace.dtos;

import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.item.Category;
import com.knitted.marketplace.models.item.ClothingSize;
import com.knitted.marketplace.models.item.Subcategory;
import com.knitted.marketplace.models.item.TargetGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ItemRequestDto {
    @NotNull
    private Shop shop;

    @NotBlank
    @Size(min = 6, max = 30, message = "Item name must be between 6 and 30 characters.")
    private String title;

    @NotBlank
    @Size(min = 30, max = 300, message = "Item description must be between 30 and 300 characters.")
    private String description;

    private Double price;
    private Category category;
    private Subcategory subcategory;
    private TargetGroup targetGroup;
    private ClothingSize clothingSize;

    private List<ImageFile> photos;

    // Constructor

    public ItemRequestDto(
            Shop shop,
            String title,
            String description,
            Double price,
            Category category,
            Subcategory subcategory,
            TargetGroup targetGroup,
            ClothingSize clothingSize,
            List<ImageFile> photos
    ) {
        this.shop = shop;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.subcategory = subcategory;
        this.targetGroup = targetGroup;
        this.clothingSize = clothingSize;
        this.photos = photos;
    }


    // Getters and Setters


    public Shop getShop() {
        return shop;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public TargetGroup getTargetGroup() {
        return targetGroup;
    }

    public ClothingSize getClothingSize() {
        return clothingSize;
    }

    public List<ImageFile> getPhotos() {
        return photos;
    }
}
