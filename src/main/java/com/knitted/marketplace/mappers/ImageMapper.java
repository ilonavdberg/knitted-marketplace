package com.knitted.marketplace.mappers;

import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.dtos.ImageRequestDto;

import java.io.IOException;

public class ImageMapper {
    public static ImageFile toImage(ImageRequestDto request) {
        ImageFile image = new ImageFile();

        image.setName(request.getName());
        try {
            image.setImageData(request.getFile().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error while processing the file", e);
        }

        return image;
    }
}
