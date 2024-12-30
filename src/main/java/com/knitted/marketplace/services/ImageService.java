package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.ImageResponseDto;
import com.knitted.marketplace.mappers.ImageMapper;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageFile uploadImage(MultipartFile file) {
        ImageFile image = ImageMapper.toImage(file);
        return imageRepository.save(image);
    }



}
