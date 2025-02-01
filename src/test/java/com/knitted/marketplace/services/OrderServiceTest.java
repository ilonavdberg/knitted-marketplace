package com.knitted.marketplace.services;

import com.knitted.marketplace.models.Customer;
import com.knitted.marketplace.models.item.Item;
import com.knitted.marketplace.models.item.ItemStatus;
import com.knitted.marketplace.models.order.Order;
import com.knitted.marketplace.models.order.OrderStatus;
import com.knitted.marketplace.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private OrderService orderService;

    private List<Order> mockOrders;
    private Item item;
    private Customer customer;

    @BeforeEach
    void setUp() {
        item = new Item(1L, "socks");
        customer = new Customer(1L, "John", "Doe");
        mockOrders = Arrays.asList(
                new Order(1L, OrderStatus.CLOSED, LocalDateTime.now(), LocalDateTime.now(), item, customer, null),
                new Order(2L, OrderStatus.CLOSED, LocalDateTime.now(), LocalDateTime.now(), null, customer, null)
        );
    }

    @Test
    void canOrderItem() {
        // arrange
        Long itemId = 1L;
        when(itemService.updateItemStatus(itemId, ItemStatus.SOLD)).thenReturn(item);
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrders.getFirst());

        // act
        Order savedOrder = orderService.orderItem(itemId, customer);

        // assert
        assertEquals(mockOrders.getFirst(), savedOrder);
    }

    @Test
    void canCreateOrderWithCorrectFields() {
        // Arrange
        Long itemId = 1L;
        when(itemService.updateItemStatus(itemId, ItemStatus.SOLD)).thenReturn(item);

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);

        // Act
        orderService.orderItem(itemId, customer);

        verify(orderRepository).save(orderArgumentCaptor.capture());

        // Assert
        Order capturedOrder = orderArgumentCaptor.getValue();

        assertEquals(OrderStatus.CLOSED, capturedOrder.getStatus());
        assertEquals(item, capturedOrder.getSoldItem());
        assertEquals(customer, capturedOrder.getCustomer());

        assertNotNull(capturedOrder.getCreatedDate());
        assertTrue(capturedOrder.getCreatedDate().isAfter(LocalDateTime.now().minusSeconds(5)));

        assertNotNull(capturedOrder.getClosedDate());
        assertTrue(capturedOrder.getClosedDate().isAfter(LocalDateTime.now().minusSeconds(5)));

    }

    @Test
    void canGetOrder() {
        // arrange
        Long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.ofNullable(mockOrders.getFirst()));

        // act
        Order fetchedOrder = orderService.getOrder(id);

        // assert
        assertEquals(mockOrders.getFirst(), fetchedOrder);
    }

    @Test
    void getOrdersForCustomer() {
        // arrange
        when(orderRepository.findByCustomerId(customer.getId())).thenReturn(mockOrders.subList(1, 2));

        // act
        List<Order> fetchedOrders = orderService.getOrdersForCustomer(customer);

        // assert
        assertEquals(mockOrders.subList(1, 2), fetchedOrders);
    }

}