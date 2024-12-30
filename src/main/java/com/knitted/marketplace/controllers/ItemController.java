package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.ItemRequestDto;
import com.knitted.marketplace.dtos.ItemResponseDto;
import com.knitted.marketplace.mappers.ImageMapper;
import com.knitted.marketplace.mappers.ItemMapper;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.item.*;
import com.knitted.marketplace.services.ItemService;
import com.knitted.marketplace.services.ShopService;
import com.knitted.marketplace.utils.Parser;

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
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "description", required = false, defaultValue = "") String description,
            @RequestParam(value = "price", required = false, defaultValue = "0") String priceInput,
            @RequestParam(value = "category", required = false, defaultValue = "") String category,
            @RequestParam(value = "subcategory", required = false, defaultValue = "") String subcategory,
            @RequestParam(value = "target", required = false, defaultValue = "") String targetGroup,
            @RequestParam(value = "size", required = false, defaultValue = "") String clothingSize,
            @RequestPart(value = "photos", required = false) Optional<List<MultipartFile>> uploadedPhotos
    ) {

        List<ImageFile> photos = ImageMapper.toImageList(uploadedPhotos.orElse(Collections.emptyList()));

        ItemRequestDto request = new ItemRequestDto(
                shopService.getShop(shopId),
                title,
                description,
                Parser.toDouble(priceInput),
                Category.fromString(category),
                Subcategory.fromString(subcategory),
                TargetGroup.fromString(targetGroup),
                ClothingSize.fromString(clothingSize),
                photos
        );

        Item item = ItemMapper.toItem(request);
        itemService.createItem(item);

        ItemResponseDto response = ItemMapper.toResponseDto(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("items/{id}")
    public ResponseEntity<ItemResponseDto> updateItem(
            @PathVariable Long id,
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "description", required = false, defaultValue = "") String description,
            @RequestParam(value = "price", required = false, defaultValue = "0") String priceInput,
            @RequestParam(value = "category", required = false, defaultValue = "") String category,
            @RequestParam(value = "subcategory", required = false, defaultValue = "") String subcategory,
            @RequestParam(value = "target", required = false, defaultValue = "") String targetGroup,
            @RequestParam(value = "size", required = false, defaultValue = "") String clothingSize,
            @RequestPart(value = "photos", required = false) Optional<List<MultipartFile>> uploadedPhotos
    ) {

        List<ImageFile> photos = ImageMapper.toImageList(uploadedPhotos.orElse(Collections.emptyList()));

        ItemRequestDto request = new ItemRequestDto(
                itemService.getItem(id).getShop(),
                title,
                description,
                Parser.toDouble(priceInput),
                Category.fromString(category),
                Subcategory.fromString(subcategory),
                TargetGroup.fromString(targetGroup),
                ClothingSize.fromString(clothingSize),
                photos
        );


        Item item = ItemMapper.toItem(request);

        itemService.updateItem(id, item);
        Item savedItem = itemService.getItem(id);

        ItemResponseDto response = ItemMapper.toResponseDto(savedItem);
        return ResponseEntity.ok(response);
    }

    //TODO: add validation check before publishing
    @PutMapping("items/{id}/publish")
    public ResponseEntity<ItemResponseDto> publishItem(@PathVariable Long id) {
        itemService.updateItemStatus(id, ItemStatus.PUBLISHED);
        Item savedItem = itemService.getItem(id);

        ItemResponseDto response = ItemMapper.toResponseDto(savedItem);
        return ResponseEntity.ok(response);
    }

    @PutMapping("items/{id}/unpublish")
    public ResponseEntity<ItemResponseDto> unpublishItem(@PathVariable Long id) {
        itemService.updateItemStatus(id, ItemStatus.NOT_PUBLISHED);
        Item savedItem = itemService.getItem(id);

        ItemResponseDto response = ItemMapper.toResponseDto(savedItem);
        return ResponseEntity.ok(response);
    }

    @PutMapping("items/{id}/archive")
    public ResponseEntity<ItemResponseDto> archiveItem(@PathVariable Long id) {
        itemService.updateItemStatus(id, ItemStatus.ARCHIVED);
        Item savedItem = itemService.getItem(id);

        ItemResponseDto response = ItemMapper.toResponseDto(savedItem);
        return ResponseEntity.ok(response);
    }
}
