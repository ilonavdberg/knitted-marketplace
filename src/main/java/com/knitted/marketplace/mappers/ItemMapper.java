package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.CatalogItemResponseDto;
import com.knitted.marketplace.dtos.ItemRequestDto;
import com.knitted.marketplace.dtos.ItemResponseDto;
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
