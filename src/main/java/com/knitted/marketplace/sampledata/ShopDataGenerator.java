package com.knitted.marketplace.sampledata;

import com.knitted.marketplace.mappers.ImageMapper;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.repositories.ShopRepository;
import org.springframework.stereotype.Component;

@Component
public class ShopDataGenerator {

    private final ShopRepository shopRepository;

    public ShopDataGenerator(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void generate() {
        Shop shop1 = new Shop();
        shop1.setName("Knitting Handmade");
        shop1.setDescription("Lorem ipsum odor amet, consectetuer adipiscing elit. Ipsum odio enim etiam venenatis et sagittis.");
        System.out.println("starting with image mapping...");
        shop1.setShopPicture(ImageMapper.toImage("static/sampledata/images/knitting_handmade.jpg"));
        shopRepository.save(shop1);

        Shop shop2 = new Shop();
        shop2.setName("Knitting with Love");
        shop2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        shop2.setShopPicture(ImageMapper.toImage("static/sampledata/images/knitting_with_love.jpg"));
        shopRepository.save(shop2);

        Shop shop3 = new Shop();
        shop3.setName("B's Knits");
        shop3.setDescription("Lorem ipsum odor amet, consectetuer adipiscing elit. Augue odio phasellus varius metus; malesuada consectetur. Leo magnis penatibus tristique; penatibus natoque class.");
        shop3.setShopPicture(ImageMapper.toImage("static/sampledata/images/b_knits.jpg"));
        shopRepository.save(shop3);
    }
}
