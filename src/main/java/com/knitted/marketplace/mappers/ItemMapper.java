package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.ItemRequestDto;
import com.knitted.marketplace.dtos.ItemResponseDto;
import com.knitted.marketplace.models.item.Item;


public class ItemMapper {
    public static Item toItem(ItemRequestDto request) {
        Item item = new Item();

        item.setShop(request.getShop());
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setCategory(request.getCategory());
        item.setSubcategory(request.getSubcategory());
        item.setTargetgroup(request.getTargetGroup());
        item.setClothingSize(request.getClothingSize());
        item.setPhotos(request.getPhotos());

        return item;
    }

    public static ItemResponseDto toResponseDto(Item item) {
        System.out.println("Converting item to ItemResponseDto...");
        System.out.println("Item Id: " + item.getId());

        return new ItemResponseDto(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getStatus().toString(),
                ShopMapper.toResponseDto(item.getShop()),
                item.getCategory().toString(),
                item.getSubcategory().toString(),
                item.getTargetgroup().toString(),
                item.getClothingSize().toString(),
                item.getPhotosToString()
        );
    }
}
