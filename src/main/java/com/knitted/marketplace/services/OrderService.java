package com.knitted.marketplace.services;

import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.item.Item;
import com.knitted.marketplace.models.item.ItemStatus;
import com.knitted.marketplace.models.order.Order;
import com.knitted.marketplace.models.order.OrderStatus;
import com.knitted.marketplace.repositories.OrderRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class OrderService {
    public final OrderRepository orderRepository;
    public final ItemService itemService;

    public OrderService(OrderRepository orderRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    @Transactional
    public Order orderItem(Long itemId, Customer customer) {
        Item item = itemService.updateItemStatus(itemId, ItemStatus.SOLD);

        return createOrder(item, customer);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Transactional
    public List<Order> getOrdersForCustomer(Customer customer) {
        List<Order> orders = orderRepository.findByCustomerId(customer.getId());

        // Eagerly initialize photos for the soldItem of each order
        for (Order order : orders) {
            Item soldItem = order.getSoldItem();
            if (soldItem != null) {
                Hibernate.initialize(soldItem.getPhotos());
            }
        }

        return orders;
    }

    private Order createOrder(Item item, Customer customer) {
        Order order = new Order();

        order.setStatus(OrderStatus.CLOSED); // currently only valid status
        order.setCreatedDate(LocalDateTime.now());
        order.setClosedDate(LocalDateTime.now());
        order.setSoldItem(item);
        order.setCustomer(customer);

        return orderRepository.save(order);
    }
}
