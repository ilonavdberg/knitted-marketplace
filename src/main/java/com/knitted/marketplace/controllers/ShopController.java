package com.knitted.marketplace.controllers;

import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.mappers.ShopMapper;
import com.knitted.marketplace.dtos.ShopRequestDto;
import com.knitted.marketplace.dtos.ShopResponseDto;

import com.knitted.marketplace.services.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/v1/shops")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    //create shop
    @PostMapping
    public ResponseEntity<ShopResponseDto> createShop(@RequestBody ShopRequestDto request) {
        Shop shop = ShopMapper.toShop(request);
        shopService.saveShop(shop);
        ShopResponseDto response = ShopMapper.toResponseDto(shop);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
