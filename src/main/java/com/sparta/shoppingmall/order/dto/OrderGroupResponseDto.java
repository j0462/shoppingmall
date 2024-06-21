package com.sparta.shoppingmall.order.dto;

import com.sparta.shoppingmall.order.entity.Order;
import com.sparta.shoppingmall.order.entity.OrderGroup;
import com.sparta.shoppingmall.order.entity.OrderStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderGroupResponseDto {
    private final Long groupId;
    private final String address;
    private final Long totalPrice;
    private final OrderStatus status;
    private final List<Order> orders;

    public OrderGroupResponseDto(OrderGroup orderGroup) {
        this.groupId = orderGroup.getId();
        this.address = orderGroup.getAddress();
        this.totalPrice = orderGroup.getTotalPrice();
        this.status = orderGroup.getStatus();
        this.orders = orderGroup.getOrders();
    }
}
