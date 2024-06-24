package com.sparta.shoppingmall.cart.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CartProductRequest {

    @NotBlank(message = "상품 아이디는 필수 값 입니다.")
    private Long productId;

}
