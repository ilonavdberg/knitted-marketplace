package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.ImageRequestDto;
import com.knitted.marketplace.mappers.ImageMapper;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.repositories.ImageRepository;
import org.springframework.stereotype.Service;


@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageFile uploadImage(ImageRequestDto request) {
        ImageFile image = ImageMapper.toImage(request);
        return imageRepository.save(image);
    }
}
