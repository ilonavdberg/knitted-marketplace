package com.knitted.marketplace.controllers;

import com.knitted.marketplace.dtos.OrderResponseDto;
import com.knitted.marketplace.mappers.OrderMapper;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.order.Order;
import com.knitted.marketplace.services.CustomerService;
import com.knitted.marketplace.services.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;


@RestController
@RequestMapping(BASE_URL)
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @PostMapping("items/{id}/order")
    public ResponseEntity<OrderResponseDto> orderItem(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("id") Long itemId
    ) {
        Customer customer = customerService.getCustomerByAuthHeader(authHeader);
        Order order = orderService.orderItem(itemId, customer, authHeader);

        OrderResponseDto response = OrderMapper.toResponseDto(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("customer/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(@RequestHeader("Authorization") String authHeader) {
        Customer customer = customerService.getCustomerByAuthHeader(authHeader);
        List<Order> orders = orderService.getOrdersForCustomer(customer);

        List<OrderResponseDto> response = OrderMapper.toResponseDtoList(orders);
        return ResponseEntity.ok(response);
    }
}
