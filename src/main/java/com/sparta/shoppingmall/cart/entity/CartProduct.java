package com.sparta.shoppingmall.cart.entity;

import com.sparta.shoppingmall.base.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "cart_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProduct extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "product_id")
    //private Product product;

    @Builder
    public CartProduct(Cart cart/*, Product product*/) {
        this.cart = cart;
        //this.product = product;
    }

    /**
     * CartProduct 생성
     */
    public static CartProduct createCartProduct(Cart cart/*, Product product*/) {

        return CartProduct.builder()
                .cart(cart)
                //.product(product)
                .build();
    }


}
