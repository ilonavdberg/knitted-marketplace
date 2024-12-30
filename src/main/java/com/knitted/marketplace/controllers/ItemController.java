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
        itemService.saveItem(item);

        ItemResponseDto response = ItemMapper.toResponseDto(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //FIXME: item id is set to null after mapping to response dto
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

        //This item has no id
        Item item = ItemMapper.toItem(request);
        System.out.println("request mapped to item object from request dto");
        //Inside the update method the database item has the id
        itemService.updateItem(id, item);
        System.out.println("item in database is updated");

        //The response dto should be based on the updated item, but this is not possible:
        Item savedItem = itemService.getItem(id);
        System.out.println("retrieved item from the database");

        //change input to item and remove above statement to get rid of error
        ItemResponseDto response = ItemMapper.toResponseDto(savedItem);
        System.out.println("map database item to response dto");
        return ResponseEntity.ok(response);
    }
}
