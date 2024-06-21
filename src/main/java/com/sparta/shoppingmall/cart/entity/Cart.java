package com.sparta.shoppingmall.cart.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart_products", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartProduct> cartProducts = new ArrayList<>();

    //@OneToOne()
    //private User user;

    @Builder
    public Cart (/*User user, */List<CartProduct> cartProducts) {
        //this.user = user;
        this.cartProducts = cartProducts;
    }

    /**
     * 장바구니에 상품 추가
     */
    public void addCartProduct(CartProduct cartProduct) {
        this.cartProducts.add(cartProduct);
    }

    /**
     * 장바구니에 상품 삭제
     */
    public void removeCartProduct(CartProduct cartProduct) {
        this.cartProducts.remove(cartProduct);
    }

    /**
     * 사용자 생성 시 장바구니 동시 생성
     */
    public static Cart createCart(/*User user*/) {
        Cart cart = new Cart();
        //cart.setUser(user);
        return cart;
    }

}
