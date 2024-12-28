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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") String priceInput,
            @RequestParam("category") Category category,
            @RequestParam("subcategory") Subcategory subcategory,
            @RequestParam("targetGroup")TargetGroup targetGroup,
            @RequestParam("clothingSize") ClothingSize clothingSize,
            @RequestPart("photos") List<MultipartFile> uploadedPhotos
    ) {
        Shop shop = shopService.getShop(shopId);
        Double price = Parser.toDouble(priceInput);
        List<ImageFile> photos = ImageMapper.toImageList(uploadedPhotos);


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
