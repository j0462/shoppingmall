package com.sparta.shoppingmall.order.dto;

import com.sparta.shoppingmall.order.entity.Orders;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderListResponseDto {
    //List에 Order를 받기 보단 OrderResponse를 받는게 어떨까요?
    private final List<Orders> orders;

    //@Builder에 대한 의미가 없는 메서드
    @Builder
    public OrderListResponseDto(List<Orders> orders) {
        this.orders = orders;
    }
}
