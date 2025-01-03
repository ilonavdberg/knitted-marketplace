package com.knitted.marketplace.services;

import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.models.item.Item;
import com.knitted.marketplace.models.item.ItemStatus;
import com.knitted.marketplace.models.order.Order;
import com.knitted.marketplace.models.order.OrderStatus;
import com.knitted.marketplace.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class OrderService {
    public final OrderRepository orderRepository;
    public final ItemService itemService;

    public OrderService(OrderRepository orderRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    @Transactional
    public Order orderItem(Long itemId) {
        Item item = itemService.updateItemStatus(itemId, ItemStatus.SOLD);

        return createOrder(item);
    }

    private Order createOrder(Item item) {
        Order order = new Order();

        order.setStatus(OrderStatus.CLOSED); // currently only valid status
        order.setCreatedDate(LocalDateTime.now());
        order.setClosedDate(LocalDateTime.now());
        order.setSoldItem(item);
        order.setCustomer(null); //TODO: change to active user's customer account

        return orderRepository.save(order);
    }


    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
