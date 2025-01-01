package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.CatalogItemResponseDto;
import com.knitted.marketplace.dtos.ItemRequestDto;
import com.knitted.marketplace.dtos.ItemResponseDto;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.item.Item;

import org.springframework.data.domain.Page;

import java.util.List;


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
        item.addPhotos(request.getPhotos());

        return item;
    }

    public static ItemResponseDto toResponseDto(Item item) {
        List<String> imageFilenames = item.getPhotos().stream().map(ImageFile::getFilename).toList();

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
                imageFilenames
        );
    }

    public static CatalogItemResponseDto toCatalogResponseDto(Item item) {
        ImageFile image = item.getPhotos().getFirst();

        return new CatalogItemResponseDto(
                item.getId(),
                item.getTitle(),
                item.getPrice(),
                image,
                item.getShop().getName(),
                item.getShop().getShopPicture()
        );
    }

    public static Page<CatalogItemResponseDto> toCatalogResponseDtoPage(Page<Item> items) {
        return items.map(ItemMapper::toCatalogResponseDto);
    }
}
