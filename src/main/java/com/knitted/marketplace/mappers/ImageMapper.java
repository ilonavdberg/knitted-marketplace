package com.knitted.marketplace.mappers;

import com.knitted.marketplace.models.ImageFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ImageMapper {
    public static ImageFile toImage(MultipartFile file) {
        ImageFile image = new ImageFile();

        image.setName(file.getName());
        try {
            image.setImageData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error while processing the file", e);
        }

        return image;
    }

    public static List<ImageFile> toImageList(List<MultipartFile> files) {
        return files.stream().map(ImageMapper::toImage).toList();
    }
}
