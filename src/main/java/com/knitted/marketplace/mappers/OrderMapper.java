package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.OrderResponseDto;
import com.knitted.marketplace.models.order.Order;

import java.util.List;

public class OrderMapper {

    public static OrderResponseDto toResponseDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getStatus().toString(),
                order.getCreatedDate().toString(),
                order.getClosedDate().toString(),
                ItemMapper.toCatalogItemResponseDto(order.getSoldItem()),
                order.getReview() != null ? order.getReview().getId() : null
        );
    }

    public static List<OrderResponseDto> toResponseDtoList(List<Order> orders) {
        return orders.stream().map(OrderMapper::toResponseDto).toList();
    }
}
