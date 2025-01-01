package com.knitted.marketplace.mappers;

import com.knitted.marketplace.dtos.OrderResponseDto;
import com.knitted.marketplace.models.order.Order;

public class OrderMapper {

    public static OrderResponseDto toResponseDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getStatus().toString(),
                order.getCreatedDate().toString(),
                order.getClosedDate().toString(),
                ItemMapper.toCatalogResponseDto(order.getSoldItem())
        );
    }
}
