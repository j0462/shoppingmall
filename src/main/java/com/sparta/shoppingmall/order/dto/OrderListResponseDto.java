package com.sparta.shoppingmall.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderListResponseDto {
    private final List<OrderResponseDto> orders;

    public OrderListResponseDto(List<OrderResponseDto> orders) {
        this.orders = orders;
    }
}
