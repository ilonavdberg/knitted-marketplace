package com.knitted.marketplace.controllers;


import com.knitted.marketplace.mappers.ImageMapper;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.mappers.ShopMapper;
import com.knitted.marketplace.dtos.ShopRequestDto;
import com.knitted.marketplace.dtos.ShopResponseDto;
import com.knitted.marketplace.services.ShopService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/v1/shops")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }


    @PostMapping()
    public ResponseEntity<ShopResponseDto> createShop(
        @RequestParam("name") String name,
        @RequestParam("description") String description,
        @RequestPart(value = "uploadedImage", required = false) MultipartFile uploadedImage) {

        ImageFile image = uploadedImage != null ? ImageMapper.toImage(uploadedImage) : null;
        System.out.println("Image file: " + image);

        ShopRequestDto request = new ShopRequestDto(name, description, image);
        Shop shop = ShopMapper.toShop(request);
        shopService.saveShop(shop);

        ShopResponseDto response = ShopMapper.toResponseDto(shop);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}


