package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.ImageResponseDto;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.utils.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

public class ImageMapper {
    public static ImageFile toImage(MultipartFile file) {
        ImageFile image = new ImageFile();

        image.setExtension(FileUtils.getFileExtension(file));
        image.setFilename(FileUtils.generateUniqueFileName(file));

        try {
            image.setImageData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error while processing the file", e);
        }

        return image;
    }

    //this method is used to create sample data
    public static ImageFile toImage(String imagePath) {
        ImageFile image = new ImageFile();
        Resource resource = new ClassPathResource(imagePath);

        try (InputStream inputStream = resource.getInputStream()) {
            byte[] imageData = inputStream.readAllBytes();
            image.setFilename(resource.getFilename());
            image.setExtension(resource.getFilename().substring(resource.getFilename().lastIndexOf(".") + 1));
            image.setImageData(imageData);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the file at " + imagePath, e);
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
                imageFile.getExtension(),
                base64Image
        );
    }

    public static List<ImageResponseDto> toResponseDtoList(List<ImageFile> imageFiles) {
        return imageFiles.stream().map(ImageMapper::toResponseDto).toList();
    }
}
