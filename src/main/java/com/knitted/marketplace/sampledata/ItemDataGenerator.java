package com.knitted.marketplace.sampledata;

import com.knitted.marketplace.mappers.ImageMapper;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.item.*;
import com.knitted.marketplace.repositories.ItemRepository;
import com.knitted.marketplace.repositories.ShopRepository;
import com.knitted.marketplace.services.ItemService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ItemDataGenerator {

    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;
    private final ItemService itemService;

    public ItemDataGenerator(ItemRepository itemRepository, ShopRepository shopRepository, ItemService itemService) {
        this.itemRepository = itemRepository;
        this.shopRepository = shopRepository;
        this.itemService = itemService;
    }

    @Transactional
    public void generate() {
        List<Shop> shops = shopRepository.findAll();
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        for (int i = 0; i < 30; i++) {
            Long id = (long) (i + 1);

            Item item = new Item();
            item.setTitle("Item " + i);
            item.setDescription(loremIpsum);
            item.setPrice(generateRandomPrice(10.0, 50.0));
            item.setShop(shops.get(i % shops.size()));

            Category category = pickRandomEnum(Category.class);
            item.setCategory(category);
            item.setSubcategory(pickRandomSubcategory(category));
            if (category.equals(Category.CLOTHING)) {
                item.setTargetgroup(pickRandomEnum(TargetGroup.class));
                item.setClothingSize(pickRandomEnum(ClothingSize.class));
            }
            item.addPhotos(Collections.singletonList(ImageMapper.toImage("static/sampledata/images/item_photo_default.png")));
            System.out.println("Images: " + item.getPhotos());

            itemRepository.save(item);
            itemService.updateItemStatus(id, ItemStatus.PUBLISHED);
        }
    }

    private double generateRandomPrice(double min, double max) {
        double price = ThreadLocalRandom.current().nextDouble(min, max);
        return Math.round(price * 100.0) / 100.0;
    }

    private <E extends Enum<E>> E pickRandomEnum(Class<E> enumClass) {
        List<E> enumConstants = Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> !e.name().equals("UNSPECIFIED"))
                .toList();

        return enumConstants.get(new Random().nextInt(enumConstants.size()));
    }

    private Subcategory pickRandomSubcategory(Category category) {
        List<Subcategory> subcategories = new ArrayList<>(category.getSubcategories());
        if (subcategories.isEmpty()) {
            return Subcategory.UNSPECIFIED; // Or handle it as needed
        }
        return subcategories.get(ThreadLocalRandom.current().nextInt(subcategories.size()));
    }
}
