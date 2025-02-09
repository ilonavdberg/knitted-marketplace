package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.ImageResponseDto;
import com.knitted.marketplace.dtos.shop.ShopRequestDto;
import com.knitted.marketplace.dtos.shop.ShopResponseDto;
import com.knitted.marketplace.dtos.shop.ShopSummaryResponseDto;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.utils.ReviewCalculator;

import org.springframework.stereotype.Component;


@Component
public class ShopMapper {

    private final ReviewCalculator reviewCalculator;

    public ShopMapper(ReviewCalculator reviewCalculator) {
        this.reviewCalculator = reviewCalculator;
    }

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

    public ShopSummaryResponseDto toSummaryResponseDto(Shop shop) {
        ImageResponseDto image = ImageMapper.toResponseDto(shop.getShopPicture());
        Long numberOfReviews = reviewCalculator.calculateNumberOfReviews(shop);
        Double averageRating = reviewCalculator.calculateAverageRating(shop);

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
