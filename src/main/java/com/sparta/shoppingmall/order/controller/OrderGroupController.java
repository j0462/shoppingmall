package com.sparta.shoppingmall.order.controller;

import com.sparta.shoppingmall.base.dto.CommonResponse;
import com.sparta.shoppingmall.order.dto.OrderGroupRequestDto;
import com.sparta.shoppingmall.order.dto.OrderGroupResponseDto;
import com.sparta.shoppingmall.order.service.OrderGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.shoppingmall.util.ControllerUtil.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderGroupController {

    private OrderGroupService orderGroupService;

    /**
     * 상품 주문
     */
    @PostMapping
    public ResponseEntity<CommonResponse<?>> createOrder(
            @RequestBody OrderGroupRequestDto orderGroupRequestDto,
            BindingResult bindingResult
            /*,@AuthenticationPrincipal UserDetailsImpl userDetails*/
    ) {
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "게시물 작성 실패");
        }
        try{
            OrderGroupResponseDto response = orderGroupService.createOrder(orderGroupRequestDto/*, userDetails*/);
            return getResponseEntity(response, "상품 주문 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    /**
     * 주문 내역 조회
     */
    @GetMapping
    public ResponseEntity<CommonResponse<?>> getOrderGroups(
            /*@AuthenticationPrincipal UserDetailsImpl userDetails*/
    ) {
        try {
            List<OrderGroupResponseDto> response = orderGroupService.getOrderGroups(/*userDetails.getUser().getId()*/);
            return getResponseEntity(response, "주문내역 조회 성공");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 주문 취소
     */
    @PutMapping("/{groupId}")
    public ResponseEntity<CommonResponse<?>> cancelOrder(
            @PathVariable Long groupId
            /*,@AuthenticationPrincipal UserDetailsImpl userDetails*/
    ) {
        try{
            Long response = orderGroupService.cancelOrder(groupId/*, userDetails.getUser()*/);
            return getResponseEntity(response, "주문 취소 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }
}
