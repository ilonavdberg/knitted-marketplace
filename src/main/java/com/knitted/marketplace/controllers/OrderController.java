package com.knitted.marketplace.controllers;

import com.knitted.marketplace.exception.exceptions.InvalidStatusChangeException;
import com.knitted.marketplace.models.item.Item;
import com.knitted.marketplace.models.item.ItemStatus;
import com.knitted.marketplace.services.ItemService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class OrderController {
    private final ItemService itemService;

    public OrderController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("items/{id}/order")
    public void orderItem(@PathVariable Long id) {
        Item item = itemService.getItem(id);
        itemService.updateItemStatus(item.getId(), ItemStatus.SOLD);


    }
}
