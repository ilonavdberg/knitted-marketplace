package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.item.CatalogItemResponseDto;
import com.knitted.marketplace.dtos.item.ItemRequestDto;
import com.knitted.marketplace.dtos.item.DetailedItemResponseDto;
import com.knitted.marketplace.dtos.item.ShopItemResponseDto;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.item.*;

import com.knitted.marketplace.utils.Parser;
import org.springframework.data.domain.Page;

import java.util.List;


public class ItemMapper {
    public static Item toItem(ItemRequestDto request) {
        Item item = new Item();

        item.setShop(request.getShop());
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setPrice(Parser.toDouble(request.getPriceInput()));
        item.setCategory(Category.fromString(request.getCategory()));
        item.setSubcategory(Subcategory.fromString(request.getSubcategory()));
        item.setTargetgroup(TargetGroup.fromString(request.getTargetGroup()));
        item.setClothingSize(ClothingSize.fromString(request.getClothingSize()));

        List<ImageFile> photos = ImageMapper.toImageList(request.getPhotos());
        item.addPhotos(photos);

        return item;
    }

    public static DetailedItemResponseDto toResponseDto(Item item) {
        List<String> imageFilenames = item.getPhotos().stream().map(ImageFile::getFilename).toList();

        return new DetailedItemResponseDto(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getStatus().toString(),
                ShopMapper.toSummaryResponseDto(item.getShop()),
                item.getCategory().toString(),
                item.getSubcategory().toString(),
                item.getTargetgroup().toString(),
                item.getClothingSize().toString(),
                imageFilenames
        );
    }

    public static CatalogItemResponseDto toCatalogItemResponseDto(Item item) {
        ImageFile image = item.getPhotos().getFirst();

        return new CatalogItemResponseDto(
                item.getId(),
                item.getTitle(),
                item.getPrice(),
                image,
                item.getShop().getName(),
                item.getShop().getShopPicture(),
                item.getStatus()
        );
    }

    public static Page<CatalogItemResponseDto> toCatalogItemResponseDtoPage(Page<Item> itemPage) {
        return itemPage.map(ItemMapper::toCatalogItemResponseDto);
    }

    public static ShopItemResponseDto toShopItemResponseDto(Item item) {
        ImageFile image = item.getPhotos().getFirst();

        return new ShopItemResponseDto(
                item.getId(),
                item.getTitle(),
                item.getPrice(),
                item.getStatus().toString(),
                image
        );
    }

    public static Page<ShopItemResponseDto> toShopItemResponseDtoPage(Page<Item> itemPage) {
        return itemPage.map(ItemMapper::toShopItemResponseDto);
    }
}
