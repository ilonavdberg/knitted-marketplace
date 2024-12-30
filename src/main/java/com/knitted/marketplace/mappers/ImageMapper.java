package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.ImageResponseDto;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.utils.FileNameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class ImageMapper {
    public static ImageFile toImage(MultipartFile file) {
        ImageFile image = new ImageFile();

        image.setFilename(FileNameUtils.generateUniqueFileName(file));
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

    public static ImageResponseDto toResponseDto(ImageFile imageFile) {
        if (imageFile == null) {
            throw new IllegalArgumentException("Image file cannot be null");
        }

        String base64Image = Base64.getEncoder().encodeToString(imageFile.getImageData());

        return new ImageResponseDto(
                imageFile.getId(),
                imageFile.getFilename(),
                base64Image
        );
    }

    public static List<ImageResponseDto> toResponseDtoList(List<ImageFile> imageFiles) {
        return imageFiles.stream().map(ImageMapper::toResponseDto).toList();
    }
}
