package com.knitted.marketplace.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ShopRequestDto {
    @NotBlank(message = "The name cannot be blank.")
    @Size(min = 6, max = 30, message = "Shop name must be between 6 and 30 characters.")
    private String name;

    @NotBlank
    @Size(min = 50, max = 300, message = "Shop description must be between 50 and 300 characters.")
    private String description;

    private ImageRequestDto imageRequest;

    //Constructor

    public ShopRequestDto(String name, String description, ImageRequestDto imageRequest) {
        this.name = name;
        this.description = description;
        this.imageRequest = imageRequest;
    }


    //Getters and Setters

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ImageRequestDto getImageRequest() {
        return imageRequest;
    }
}
