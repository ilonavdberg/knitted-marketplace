package com.knitted.marketplace.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class ImageRequestDto {
    @NotBlank(message = "Filename cannot be blank.")
    @Size(min = 3, max = 30, message = "The name must be between 3 and 30 characters.")
    private String filename;

    @NotNull(message = "File cannot be null.")
    private MultipartFile file;

    //Getters

    public String getName() {
        return filename;
    }

    public MultipartFile getFile() {
        return file;
    }
}

