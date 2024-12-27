package com.knitted.marketplace.services;

import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.repositories.ShopRepository;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void saveShop(Shop shop) {
        shopRepository.save(shop);
    }
}
