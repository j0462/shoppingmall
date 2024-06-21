package com.sparta.shoppingmall.order.service;

import com.sparta.shoppingmall.order.dto.OrderGroupRequestDto;
import com.sparta.shoppingmall.order.dto.OrderGroupResponseDto;
import com.sparta.shoppingmall.order.entity.OrderGroup;
import com.sparta.shoppingmall.order.entity.OrderStatus;
import com.sparta.shoppingmall.order.repository.OrderGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderGroupService {

    private final OrderGroupRepository orderGroupRepository;

    /**
     * 주문하기 (OrderGroup생성 -> Order생성)
     */
    @Transactional
    public OrderGroupResponseDto createOrder(OrderGroupRequestDto request/*, UserDetailsImpl userDetails*/) {
        OrderGroup orderGroup = OrderGroup.builder()
                .address(request.getAddress())
                .totalPrice(request.getTotalPrice())
                .status(OrderStatus.ORDERED)
                .build();

        //Order order = createOrder(orderGroup);
        //addOrder(order);
        orderGroupRepository.save(orderGroup);

        //상품 상태 변경 로직 추가 예정

        OrderGroupResponseDto response = new OrderGroupResponseDto(orderGroup);

        return new OrderGroupResponseDto(orderGroup);
    }

    /**
     * 주문내역 조회하기
     */
    @Transactional(readOnly = true)
    public List<OrderGroupResponseDto> getOrderGroups(/*Long userId*/) {
        List<OrderGroup> orderGroups = orderGroupRepository.findByUserId(1L/*userId*/).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자의 주문 내역이 존재하지 않습니다.")
        );

        List<OrderGroupResponseDto> list = new ArrayList<>();

        for(OrderGroup orderGroup : orderGroups){
            list.add(new OrderGroupResponseDto(orderGroup));
        }

        return list;
    }

    /**
     * 주문 취소하기
     */
    @Transactional
    public Long cancelOrder(Long groupId/*, User user*/) {
        OrderGroup orderGroup = orderGroupRepository.findByGroupId(1L/*groupId*/).orElseThrow(
                () -> new IllegalArgumentException("해당 주문 내역이 존재하지 않습니다.")
        );
        orderGroup.updateStatus(OrderStatus.CANCELED);

        //상품 상태 변경 로직 추가 예정

        return orderGroup.getId();
    }
}
