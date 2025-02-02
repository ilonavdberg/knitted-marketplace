package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.shop.ShopCreatedResponseDto;
import com.knitted.marketplace.dtos.shop.ShopSummaryResponseDto;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.mappers.ShopMapper;
import com.knitted.marketplace.dtos.shop.ShopRequestDto;
import com.knitted.marketplace.dtos.shop.ShopResponseDto;
import com.knitted.marketplace.services.ShopService;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
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


    @PostMapping
    public ResponseEntity<ShopResponseDto> createShop(
            @RequestHeader("Authorization") String authHeader,
            @Valid @ModelAttribute ShopRequestDto request,
            @RequestPart(value = "uploadedImage") MultipartFile uploadedImage
    ) {
        request.setUploadedImage(uploadedImage);

        ShopCreatedResponseDto response = shopService.createShop(request, authHeader);
        ShopResponseDto updatedShop = ShopMapper.toResponseDto(response.shop());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + response.token());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(updatedShop);
    }

    @GetMapping("{id}/profile")
    public ResponseEntity<ShopSummaryResponseDto> getShopSummary(@PathVariable Long id) {
        Shop shop = shopService.getShop(id);
        ShopSummaryResponseDto response = ShopMapper.toSummaryResponseDto(shop);
        return ResponseEntity.ok(response);
    }

}


