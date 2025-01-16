package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.item.CatalogItemResponseDto;
import com.knitted.marketplace.dtos.item.ItemRequestDto;
import com.knitted.marketplace.dtos.item.DetailedItemResponseDto;
import com.knitted.marketplace.dtos.item.ShopItemResponseDto;
import com.knitted.marketplace.mappers.ItemMapper;
import com.knitted.marketplace.models.item.*;
import com.knitted.marketplace.services.ItemService;
import com.knitted.marketplace.services.ShopService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;

//TODO: add @Valid annotations to all Controller methods that receive client input

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
    public ResponseEntity<DetailedItemResponseDto> createItem(
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

        ItemRequestDto request = new ItemRequestDto(
                shopService.getShop(shopId),
                title,
                description,
                priceInput,
                category,
                subcategory,
                targetGroup,
                clothingSize,
                uploadedPhotos.orElse(Collections.emptyList())
        );

        Item savedItem = itemService.createItem(request);
        DetailedItemResponseDto response = ItemMapper.toResponseDto(savedItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("items/{id}")
    public ResponseEntity<DetailedItemResponseDto> updateItem(
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

        ItemRequestDto request = new ItemRequestDto(
                itemService.getItem(id).getShop(),
                title,
                description,
                priceInput,
                category,
                subcategory,
                targetGroup,
                clothingSize,
                uploadedPhotos.orElse(Collections.emptyList())
        );

        Item savedItem = itemService.updateItem(id, request);
        DetailedItemResponseDto response = ItemMapper.toResponseDto(savedItem);

        return ResponseEntity.ok(response);
    }

    //TODO: add validation check before publishing
    @PutMapping("items/{id}/publish")
    public ResponseEntity<DetailedItemResponseDto> publishItem(@PathVariable Long id) {
        Item savedItem = itemService.updateItemStatus(id, ItemStatus.PUBLISHED);

        DetailedItemResponseDto response = ItemMapper.toResponseDto(savedItem);
        return ResponseEntity.ok(response);
    }

    @PutMapping("items/{id}/unpublish")
    public ResponseEntity<DetailedItemResponseDto> unpublishItem(@PathVariable Long id) {
        Item savedItem = itemService.updateItemStatus(id, ItemStatus.NOT_PUBLISHED);

        DetailedItemResponseDto response = ItemMapper.toResponseDto(savedItem);
        return ResponseEntity.ok(response);
    }

    @PutMapping("items/{id}/archive")
    public ResponseEntity<DetailedItemResponseDto> archiveItem(@PathVariable Long id) {
        Item savedItem = itemService.updateItemStatus(id, ItemStatus.ARCHIVED);

        DetailedItemResponseDto response = ItemMapper.toResponseDto(savedItem);
        return ResponseEntity.ok(response);
    }

    @GetMapping("items")
    public ResponseEntity<Page<CatalogItemResponseDto>> getAllItemsForSale(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(required = false, defaultValue = "") String subcategory,
            @RequestParam(required = false, defaultValue = "") String target,
            @RequestParam(value = "price", required = false, defaultValue = "") String priceRange,
            @RequestParam(required = false, defaultValue = "") String size,
            @PageableDefault(size = 20) Pageable pageable
    ) {

        Page<Item> itemPage = itemService.getItemsForSale(keyword, category, subcategory, target, priceRange, size, pageable);
        Page<CatalogItemResponseDto> response = ItemMapper.toCatalogItemResponseDtoPage(itemPage);

        return ResponseEntity.ok(response);
    }

    @GetMapping("items/{id}")
    public ResponseEntity<DetailedItemResponseDto> getItem(@PathVariable Long id) {
        Item item = itemService.getItem(id);
        DetailedItemResponseDto response = ItemMapper.toResponseDto(item);
        return ResponseEntity.ok(response);
    }

    @GetMapping("shops/{id}/items")
    public ResponseEntity<Page<ShopItemResponseDto>> getItemsForShop(
            @PathVariable("id") Long shopId,
            @RequestParam(required = false, defaultValue = "") String status,
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(required = false, defaultValue = "") String subcategory,
            @RequestParam(value = "price", required = false, defaultValue = "") String priceRange,
            @RequestParam(required = false, defaultValue = "") String target,
            @RequestParam(required = false, defaultValue = "") String size,
            @PageableDefault(size = 24) Pageable pageable
            ) {

        Page<Item> itemPage = itemService.getItemsForShop(shopId, status, category, subcategory, priceRange, target, size, pageable);
        Page<ShopItemResponseDto> response = ItemMapper.toShopItemResponseDtoPage(itemPage);

        return ResponseEntity.ok(response);
    }
}
