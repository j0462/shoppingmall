package com.sparta.shoppingmall.product.dto;

import com.sparta.shoppingmall.product.controller.entity.Product;
import com.sparta.shoppingmall.product.controller.entity.ProductStatus;
import com.sparta.shoppingmall.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;
    private final Long userId;
    private final String username;
    private final String name;
    private final Long price;
    private final ProductStatus status;
    private final int likeCount;

    @Builder
    public ProductResponse(Long id, String name, Long price, ProductStatus status, User user, int likeCount) {
        this.id = id;
        this.userId = user.getId();
        this.username = user.getUsername(); //사용자 ID
        this.name = name;
        this.price = price;
        this.status = status;
        this.likeCount = likeCount;
    }

    public static ProductResponse from(Product product, User user) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus())
                .user(user)
                .likeCount(product.getLikeCount())
                .build();
    }
}