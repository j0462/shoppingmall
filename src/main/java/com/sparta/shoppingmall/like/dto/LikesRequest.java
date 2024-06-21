package com.sparta.shoppingmall.like.dto;

import com.sparta.shoppingmall.like.entity.ContentType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikesRequest {

    @NotBlank(message = "컨텐츠 타입 값이 없습니다.")
    private ContentType contentType;

    @NotBlank(message = "컨텐츠 id 값이 없습니다.")
    private Long contentId;

}
