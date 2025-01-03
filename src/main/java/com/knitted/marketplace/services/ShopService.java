package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.shop.ShopRequestDto;
import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.mappers.ShopMapper;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.repositories.ShopRepository;
import org.springframework.stereotype.Service;


@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Shop saveShop(ShopRequestDto request) {
        Shop shop = ShopMapper.toShop(request);


        return shopRepository.save(shop);
    }

    public Shop getShop(Long id) {
        return shopRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
