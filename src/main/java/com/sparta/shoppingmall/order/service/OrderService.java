package com.sparta.shoppingmall.order.service;

import com.sparta.shoppingmall.cart.entity.CartProduct;
import com.sparta.shoppingmall.order.dto.OrderListResponseDto;
import com.sparta.shoppingmall.order.dto.OrderRequestDto;
import com.sparta.shoppingmall.order.dto.OrderResponseDto;
import com.sparta.shoppingmall.order.entity.Order;
import com.sparta.shoppingmall.order.repository.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto createOrder(
            @Valid @RequestBody OrderRequestDto orderRequestDto/*,
            @AuthenticationPrincipal UserDetailsImpl userDetails*/)
    {
        Order order = Order.builder()
                /*.user(userDetails.getUser())*/
                .address(orderRequestDto.getAddress())
                .totalPrice(orderRequestDto.getTotalPrice())
                .build();
        order.approvedStatus();
        orderRepository.save(order);
        return new OrderResponseDto(order);
    }

    public OrderListResponseDto getOrdersByUserId(
            /*@AuthenticationPrincipal UserDetailsImpl userDetails*/)
    {
        List<OrderResponseDto> orders = orderRepository.findByUserId(1L/*userDetails.getUser().getId()*/);
        return new OrderListResponseDto(orders);
    }

    @Transactional
    public Long cancelOrder(
            Long orderId/*,
            @AuthenticationPrincipal UserDetailsImpl userDetails*/)
    {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("해당 주문은 존재하지 않습니다.")
        );

        /*if (order.getUser().getId().equals(userDetails.getUser().getId())) {
                order.canceledStatus();
                orderRepository.save(order);
                return order.getUser().getId();
        } else {
                throw new new IllegalArgumentException("해당 주문은 존재하지 않습니다.")
        }*/
        return 1L;
    }
}
