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

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;


@RestController
@RequestMapping(BASE_URL + "/shops")
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

        ShopRequestDto request = new ShopRequestDto(name, description, image);
        Shop shop = ShopMapper.toShop(request);

        Shop updatedShop = shopService.saveShop(shop);

        ShopResponseDto response = ShopMapper.toResponseDto(updatedShop);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}


