package com.sparta.shoppingmall.domain.cart.entity;

import com.sparta.shoppingmall.domain.user.entity.User;
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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CartProduct> cartProducts = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Cart (User user) {
        this.user = user;
    }

    /**
     * 장바구니 생성
     */
    public static Cart createCart(User user) {
        return Cart.builder()
                .user(user)
                .build();
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

}
