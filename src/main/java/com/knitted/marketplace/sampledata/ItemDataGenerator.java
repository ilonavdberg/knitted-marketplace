package com.knitted.marketplace.sampledata;

import com.knitted.marketplace.mappers.ImageMapper;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.User;
import com.knitted.marketplace.models.item.*;
import com.knitted.marketplace.repositories.ItemRepository;
import com.knitted.marketplace.repositories.ShopRepository;

import com.knitted.marketplace.security.JwtService;
import com.knitted.marketplace.services.ItemService;
import com.knitted.marketplace.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


@Component
public class ItemDataGenerator {

    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final JwtService jwtService;

    public ItemDataGenerator(ItemService itemService, ItemRepository itemRepository, UserService userService, JwtService jwtService) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Transactional
    public void generate() {
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        List<User> users = List.of(
                userService.getUserById(1L),
                userService.getUserById(2L)
        );

        List<String> tokens = List.of(
                jwtService.generateToken(users.getFirst(), users.getFirst().getRoles()),
                jwtService.generateToken(users.getLast(), users.getLast().getRoles())
        );

        for (int i = 1; i < 51; i++) {
            User user = (i % 2 == 0) ? users.getLast() : users.getFirst();
            String token = (i % 2 == 0) ? tokens.getLast() : tokens.getFirst();

            Item item = new Item();
            item.setTitle("Item " + i);
            item.setDescription(loremIpsum);
            item.setPrice(generateRandomPrice(10.0, 50.0));
            item.setShop(user.getContact().getShop());

            Category category = pickRandomEnum(Category.class);
            item.setCategory(category);
            item.setSubcategory(pickRandomSubcategory(category));
            if (category.equals(Category.CLOTHING)) {
                item.setTargetgroup(pickRandomEnum(TargetGroup.class));
                item.setClothingSize(pickRandomEnum(ClothingSize.class));
            }
            item.addPhotos(Collections.singletonList(ImageMapper.toImage("static/sampledata/images/item_photo_default.png")));

            Item savedItem = itemRepository.save(item);
            itemService.updateItemStatus(savedItem.getId(), ItemStatus.PUBLISHED, "Bearer " + token);
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
            return Subcategory.UNSPECIFIED;
        }
        return subcategories.get(ThreadLocalRandom.current().nextInt(subcategories.size()));
    }
}
