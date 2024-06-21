package com.sparta.shoppingmall.order.dto;

import com.sparta.shoppingmall.order.entity.Order;
import com.sparta.shoppingmall.order.status.OrderStatus;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    private final Long orderId;
    /*private final User user;*/
    private final String address;
    private final int totalPrice;
    private final OrderStatus status;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        /*this.User = order.getUser();*/
        this.address = order.getAddress();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
    }
}
