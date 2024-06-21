package com.sparta.shoppingmall.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    @NotBlank(message = "배달 주소는 공백이 될 수 없습니다.")
    private String address;
    @NotBlank(message = "지불액은 공백이 될 수 없습니다.")
    private int totalPrice;
}
