package com.knitted.marketplace.dtos;


import com.knitted.marketplace.models.Shop;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class ItemRequestDto {
    @NotNull
    private Shop shop;

    // Validation happens before publishing
    private String title;
    private String description;
    private String priceInput;

    private String category;
    private String subcategory;
    private String targetGroup;
    private String clothingSize;

    private List<MultipartFile> photos;

    // Constructor

    public ItemRequestDto(
            Shop shop,
            String title,
            String description,
            String priceInput,
            String category,
            String subcategory,
            String targetGroup,
            String clothingSize,
            List<MultipartFile> photos
    ) {
        this.shop = shop;
        this.title = title;
        this.description = description;
        this.priceInput = priceInput;
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

    public String getPriceInput() {
        return priceInput;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public String getClothingSize() {
        return clothingSize;
    }

    public List<MultipartFile> getPhotos() {
        return photos;
    }
}
