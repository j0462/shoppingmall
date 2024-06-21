package com.sparta.shoppingmall.order.repository;

import com.sparta.shoppingmall.order.dto.OrderResponseDto;
import com.sparta.shoppingmall.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<OrderResponseDto> findByUserId(Long userId);
}
