package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.ShopRequestDto;
import com.knitted.marketplace.dtos.ShopResponseDto;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.Shop;

public class ShopMapper {
    public static Shop toShop(ShopRequestDto request) {
        Shop shop = new Shop();

        shop.setName(request.getName());
        shop.setDescription(request.getDescription());

        if (request.getImageRequest() != null) {
            ImageFile image = ImageMapper.toImage(request.getImageRequest());
            shop.setShopPicture(image);
        }

        return shop;
    }

    public static ShopResponseDto toResponseDto(Shop shop) {
        return new ShopResponseDto(
                shop.getId(),
                shop.getName(),
                shop.getDescription(),
                shop.getItems(),
                shop.getOwner()
        );
    }
}
