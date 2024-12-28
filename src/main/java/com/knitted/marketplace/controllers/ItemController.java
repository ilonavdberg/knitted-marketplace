package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.ItemRequestDto;
import com.knitted.marketplace.dtos.ItemResponseDto;
import com.knitted.marketplace.mappers.ImageMapper;
import com.knitted.marketplace.mappers.ItemMapper;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.item.*;
import com.knitted.marketplace.services.ItemService;
import com.knitted.marketplace.services.ShopService;

import com.knitted.marketplace.utils.Parser;
import org.hibernate.annotations.Collate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;


@RestController
@RequestMapping(BASE_URL)
public class ItemController {
    private final ItemService itemService;
    private final ShopService shopService;

    public ItemController(ItemService itemService, ShopService shopService) {
        this.itemService = itemService;
        this.shopService = shopService;
    }

    //TODO: check if shopId can be derived from User token instead of path variable
    @PostMapping("shops/{shopId}/items")
    public ResponseEntity<ItemResponseDto> createItem(
            @PathVariable Long shopId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) String priceInput,
            @RequestParam(value = "category", required = false) Category category,
            @RequestParam(value = "subcategory", required = false) Subcategory subcategory,
            @RequestParam(value = "target", required = false)TargetGroup targetGroup,
            @RequestParam(value = "size", required = false) ClothingSize clothingSize,
            @RequestPart(value = "photos", required = false) Optional<List<MultipartFile>> uploadedPhotos
    ) {
        Shop shop = shopService.getShop(shopId);
        Double price = Parser.toDouble(priceInput);
        List<ImageFile> photos = ImageMapper.toImageList(uploadedPhotos.orElse(Collections.emptyList()));


        ItemRequestDto request = new ItemRequestDto(
                shop,
                title,
                description,
                price,
                category,
                subcategory,
                targetGroup,
                clothingSize,
                photos
        );

        Item item = ItemMapper.toItem(request);
        itemService.saveItem(item);

        ItemResponseDto response = ItemMapper.toResponseDto(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
