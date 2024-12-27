package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.ItemRequestDto;
import com.knitted.marketplace.mappers.ImageMapper;
import com.knitted.marketplace.mappers.ItemMapper;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.item.*;
import com.knitted.marketplace.services.ItemService;

import com.knitted.marketplace.services.ShopService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;


@RestController
@RequestMapping(BASE_URL + "shops")
public class ItemController {
    private final ItemService itemService;
    private final ShopService shopService;

    public ItemController(ItemService itemService, ShopService shopService) {
        this.itemService = itemService;
        this.shopService = shopService;
    }

    @PostMapping("/{shopId}/items")
    public ResponseEntity<Item> createItem(
            @PathVariable Long shopId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("category") Category category,
            @RequestParam("subcategory") Subcategory subcategory,
            @RequestParam("targetGroup")TargetGroup targetGroup,
            @RequestParam("clothingSize") ClothingSize clothingSize,
            @RequestPart("photos") List<MultipartFile> uploadedPhotos
    ) {
        List<ImageFile> photos = ImageMapper.toImageList(uploadedPhotos);
        Shop shop = shopService.getShop(shopId);

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

        return ResponseEntity.status(HttpStatus.CREATED).body(item);

        //create response Dto and return it

    }
}
