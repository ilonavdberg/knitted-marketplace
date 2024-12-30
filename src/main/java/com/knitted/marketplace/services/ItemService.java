package com.knitted.marketplace.services;

import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.models.item.Item;
import com.knitted.marketplace.models.item.ItemStatus;
import com.knitted.marketplace.repositories.ItemRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveItem(Item item) {
        item.setStatus(ItemStatus.NOT_PUBLISHED);
        itemRepository.save(item);
    }

    public void updateItem(Long id, Item updatedItem) {
        Item item = getItem(id);
        System.out.println("Inside updateItem method id is: " + item.getId());

        item.setTitle(updatedItem.getTitle());
        item.setDescription(updatedItem.getDescription());
        item.setPrice(updatedItem.getPrice());
        item.setCategory(updatedItem.getCategory());
        item.setSubcategory(updatedItem.getSubcategory());
        item.setTargetgroup(updatedItem.getTargetgroup());
        item.setClothingSize(updatedItem.getClothingSize());
        item.addPhotos(updatedItem.getPhotos());

        itemRepository.save(item);
    }

    public Item getItem(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
