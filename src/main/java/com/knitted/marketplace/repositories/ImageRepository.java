package com.knitted.marketplace.repositories;

import com.knitted.marketplace.models.ImageFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageFile, Long> {
}
