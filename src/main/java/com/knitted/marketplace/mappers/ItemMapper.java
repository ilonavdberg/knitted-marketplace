package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.ItemRequestDto;
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
}
