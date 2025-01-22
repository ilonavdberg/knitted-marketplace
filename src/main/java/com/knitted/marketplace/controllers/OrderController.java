package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.OrderResponseDto;
import com.knitted.marketplace.mappers.OrderMapper;
import com.knitted.marketplace.models.order.Order;
import com.knitted.marketplace.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<OrderResponseDto> orderItem(@PathVariable("id") Long itemId) {
        Order order = orderService.orderItem(itemId);

        OrderResponseDto response = OrderMapper.toResponseDto(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
