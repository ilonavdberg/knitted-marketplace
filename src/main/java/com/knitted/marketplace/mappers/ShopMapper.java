package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.ImageResponseDto;
import com.knitted.marketplace.dtos.shop.ShopRequestDto;
import com.knitted.marketplace.dtos.shop.ShopResponseDto;
import com.knitted.marketplace.dtos.shop.ShopSummaryResponseDto;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.item.ItemStatus;
import org.hibernate.Hibernate;

import java.awt.*;
import java.util.stream.Collectors;


public class ShopMapper {
    public static Shop toShop(ShopRequestDto request) {
        Shop shop = new Shop();

        shop.setName(request.getName());
        shop.setDescription(request.getDescription());

        ImageFile image = request.getUploadedImage() != null ? ImageMapper.toImage(request.getUploadedImage()) : null;
        shop.setShopPicture(image);

        return shop;
    }

    public static ShopResponseDto toResponseDto(Shop shop) {
        ImageFile imageFile = shop.getShopPicture();
        ImageResponseDto image = ImageMapper.toResponseDto(imageFile);

        return new ShopResponseDto(
                shop.getId(),
                shop.getName(),
                shop.getDescription(),
                shop.getItemsToString(),
                shop.getOwnerToString(),
                image
        );
    }

    public static ShopSummaryResponseDto toSummaryResponseDto(Shop shop) {
        Hibernate.initialize(shop.getItems());

        shop.getItems().forEach(item -> {
            Hibernate.initialize(item.getOrder());
            if (item.getOrder() != null) {
                Hibernate.initialize(item.getOrder().getReview());
            }
        });

        Long numberOfReviews = shop.getItems().stream()
                .filter(item -> item.getStatus().equals(ItemStatus.SOLD))
                .filter(item -> item.getOrder().getReview() != null)
                .count();

        Double averageRating = shop.getItems().stream()
                .filter(item -> item.getStatus().equals(ItemStatus.SOLD))
                .filter(item -> item.getOrder().getReview() != null)
                .mapToInt(item -> item.getOrder().getReview().getRating())
                .average()
                .orElse(0);

        ImageResponseDto image = ImageMapper.toResponseDto(shop.getShopPicture());

        return new ShopSummaryResponseDto(
                shop.getId(),
                shop.getName(),
                shop.getDescription(),
                image,
                numberOfReviews,
                averageRating
        );
    }
}
