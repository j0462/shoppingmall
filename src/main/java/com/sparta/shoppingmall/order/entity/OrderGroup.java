package com.sparta.shoppingmall.order.entity;

import com.sparta.shoppingmall.base.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderGroup extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    //User와 매핑

    //Product와 매핑
    //@OneToMany(mappedBy = "products")
    //private List<Product> products = new ArrayList<>();

    /**
     * 생성자
     */
    @Builder
    public OrderGroup (String address, Long totalPrice, OrderStatus status) {
        this.address = address;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    /**
     * 주문 상태 업데이트
     */
    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * List<Order>에 Order추가하기
     */
    public void addOrder(Order order) {
        this.orders.add(order);
    }

}
