package com.sparta.shoppingmall.like.controller;

import com.sparta.shoppingmall.base.dto.CommonResponse;
import com.sparta.shoppingmall.like.dto.LikesRequest;
import com.sparta.shoppingmall.like.dto.LikesResponse;
import com.sparta.shoppingmall.like.service.LikesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.sparta.shoppingmall.util.ControllerUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/products/{productId}/like")
    public ResponseEntity<CommonResponse<?>> toggleProductLike (
            @PathVariable Long productId,
            @Valid @RequestBody LikesRequest request,
            BindingResult bindingResult
            //@AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
       if(bindingResult.hasErrors()) {
           return getFieldErrorResponseEntity(bindingResult, "상품 좋아요 토글 실패");
       }
       try{
           LikesResponse response = likesService.toggleLike(request/*, userDetails.getUser().getId()*/);
           return getResponseEntity(response, "상품 좋아요 토글 성공");
       } catch (Exception e) {
           return getBadRequestResponseEntity(e);
       }
    }

}
