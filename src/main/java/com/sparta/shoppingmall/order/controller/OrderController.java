package com.sparta.shoppingmall.order.controller;

import com.sparta.shoppingmall.base.dto.CommonResponse;
import com.sparta.shoppingmall.cart.dto.CartProductResponse;
import com.sparta.shoppingmall.order.dto.OrderListResponseDto;
import com.sparta.shoppingmall.order.dto.OrderRequestDto;
import com.sparta.shoppingmall.order.dto.OrderResponseDto;
import com.sparta.shoppingmall.order.entity.Order;
import com.sparta.shoppingmall.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.shoppingmall.util.ControllerUtil.*;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //상품 주문
    @PostMapping
    public ResponseEntity<CommonResponse<?>> createOrder(
            @Valid @RequestBody OrderRequestDto orderRequestDto/*,
            @AuthenticationPrincipal UserDetailsImpl userDetails*/,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "Order Fail");
        }
        try{
            OrderResponseDto response = orderService.createOrder(orderRequestDto/*, userDetails*/);
            return getResponseEntity(response, "Order Success");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    //주문내역 조회
    @GetMapping
    public ResponseEntity<CommonResponse<?>> getOrdersByUserId(
            /*@AuthenticationPrincipal UserDetailsImpl userDetails*/)
    {
        try{
            OrderListResponseDto response = orderService.getOrdersByUserId(/*userDetails*/);
            return getResponseEntity(response, "Order Success");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    //주문 취소
    @DeleteMapping("/{orderid}")
    public ResponseEntity<CommonResponse<?>> cancelOrder(
            @PathVariable Long orderId/*,
            @AuthenticationPrincipal UserDetailsImpl userDetails*/)
    {
        try{
            Long response = orderService.cancelOrder(orderId/*, userDetails.getUser().getId()*/);
            return getResponseEntity(response, "장바구니에 상품 삭제 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }
}
