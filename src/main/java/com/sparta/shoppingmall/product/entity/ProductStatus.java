package com.sparta.shoppingmall.product.entity;

import lombok.Getter;

@Getter
public enum ProductStatus {
    ONSALE("Onsale"),//
    INPROGRESS("Inprogress"),
    COMPLETED("Completed");

    private final String status;

    ProductStatus(String status) {
        this.status = status;
    }
}
