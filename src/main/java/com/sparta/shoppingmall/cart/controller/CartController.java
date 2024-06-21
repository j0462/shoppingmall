package com.sparta.shoppingmall.cart.controller;

import com.sparta.shoppingmall.base.dto.CommonResponse;
import com.sparta.shoppingmall.cart.dto.CartProductRequest;
import com.sparta.shoppingmall.cart.dto.CartProductResponse;
import com.sparta.shoppingmall.cart.dto.CartResponse;
import com.sparta.shoppingmall.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.sparta.shoppingmall.util.ControllerUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니에 상품 단건 담기
     */
    @PostMapping
    public ResponseEntity<CommonResponse<?>> creatCartProduct(
            @Valid @RequestBody CartProductRequest cartProductRequest,
            //@AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "장바구니에 상품 추가 실패");
        }
        try{
            CartProductResponse response = cartService.addCartProduct(cartProductRequest/*, userDetails.getUser().getId()*/);
            return getResponseEntity(response, "장바구니에 상품 추가 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    /**
     * 장바구니에 상품 리스트 조회
     */
    @GetMapping
    public ResponseEntity<CommonResponse<?>> getCartProducts(
            @PageableDefault(
                    size = 5,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
            //@AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try{
            CartResponse response = cartService.getCartProducts(pageable/*, userDetails.getUser().getId()*/);
            return getResponseEntity(response, "장바구니 조회 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    /**
     * 장바구니에 상품 단건 삭제
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<CommonResponse<?>> deleteCartProduct(
            @PathVariable Long productId
            //@AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try{
            Long response = cartService.deleteCartProduct(productId/*, userDetails.getUser().getId()*/);
            return getResponseEntity(response, "장바구니에 상품 삭제 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

}
