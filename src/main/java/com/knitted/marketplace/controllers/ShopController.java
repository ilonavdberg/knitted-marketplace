package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.ImageRequestDto;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.mappers.ShopMapper;
import com.knitted.marketplace.dtos.ShopRequestDto;
import com.knitted.marketplace.dtos.ShopResponseDto;

import com.knitted.marketplace.services.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/shops")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

//    //create shop
//    @PostMapping()
//    public ResponseEntity<ShopResponseDto> createShop(@RequestBody ShopRequestDto request) {
//        Shop shop = ShopMapper.toShop(request);
//        shopService.saveShop(shop);
//        ShopResponseDto response = ShopMapper.toResponseDto(shop);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

    //create shop test
    @PostMapping()
    public ResponseEntity<ShopResponseDto> createShop(
        @RequestParam("name") String name,
        @RequestParam("description") String description,
        @RequestPart(value = "imageRequest", required = false) ImageRequestDto imageRequest) {
    ShopRequestDto request = new ShopRequestDto(name, description, imageRequest);
    Shop shop = ShopMapper.toShop(request);
    shopService.saveShop(shop);
    ShopResponseDto response = ShopMapper.toResponseDto(shop);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

}


