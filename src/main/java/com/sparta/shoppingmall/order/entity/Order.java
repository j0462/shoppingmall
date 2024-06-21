package com.sparta.shoppingmall.order.entity;

import com.sparta.shoppingmall.base.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long productPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private OrderGroup orderGroup;

    @Builder
    public Order(String productName, Long productPrice, OrderGroup orderGroup) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.orderGroup = orderGroup;
    }

    /**
     * 주문 그룹생성 시 주문 생성
     */
//    public static Order createOrder(OrderGroup orderGroup/*, Product product*/) {
//        return Order.builder()
//                .productName(/*product.getName()*/)
//                .productPricep(/*product.getPrice()*/)
//                .orderGroup(orderGroup)
//                .build();
//    }

}
