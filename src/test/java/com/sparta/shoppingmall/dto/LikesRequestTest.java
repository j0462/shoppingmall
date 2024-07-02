package com.sparta.shoppingmall.dto;

import com.sparta.shoppingmall.like.dto.LikesRequest;
import com.sparta.shoppingmall.like.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.*;

class LikesRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        this.validator = factoryBean;
    }

    @Test
    void testValidLikesRequest() {
        LikesRequest likesRequest = new LikesRequest(ContentType.PRODUCT, 1L);
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(likesRequest, "likesRequest");
        validator.validate(likesRequest, errors);
        assertFalse(errors.hasErrors(), "유효한 LikesRequest에서 오류가 없어야 합니다.");
    }

    @Test
    void testContentTypeIsNull() {
        LikesRequest likesRequest = new LikesRequest(null, 1L);
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(likesRequest, "likesRequest");
        validator.validate(likesRequest, errors);
        assertTrue(errors.hasErrors(), "ContentType이 null일 때 오류가 발생해야 합니다.");
        assertNotNull(errors.getFieldError("contentType"), "ContentType 필드에서 오류가 발생해야 합니다.");
        assertEquals("컨텐츠 유형을 입력하세요", errors.getFieldError("contentType").getDefaultMessage());
    }

    @Test
    void testContentIdIsNull() {
        LikesRequest likesRequest = new LikesRequest(ContentType.PRODUCT, null);
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(likesRequest, "likesRequest");
        validator.validate(likesRequest, errors);
        assertTrue(errors.hasErrors(), "ContentId가 null일 때 오류가 발생해야 합니다.");
        assertNotNull(errors.getFieldError("contentId"), "ContentId 필드에서 오류가 발생해야 합니다.");
        assertEquals("컨텐츠 id 값이 없습니다.", errors.getFieldError("contentId").getDefaultMessage());
    }
}
