package com.knitted.marketplace.controllers;

import com.knitted.marketplace.models.order.Order;
import com.knitted.marketplace.services.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;


@RestController
@RequestMapping(BASE_URL)
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("items/{id}/order")
    public void orderItem(@PathVariable("id") Long itemId) {
        Order order = orderService.orderItem(itemId);
    }
}