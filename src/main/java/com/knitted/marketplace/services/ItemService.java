package com.knitted.marketplace.services;

import com.knitted.marketplace.models.item.Item;
import com.knitted.marketplace.repositories.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }
}
