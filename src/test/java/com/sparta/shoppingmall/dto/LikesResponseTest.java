package com.sparta.shoppingmall.dto;

import com.sparta.shoppingmall.like.dto.LikesResponse;
import com.sparta.shoppingmall.like.entity.ContentType;
import com.sparta.shoppingmall.like.entity.LikeStatus;
import com.sparta.shoppingmall.like.entity.Likes;
import com.sparta.shoppingmall.user.entity.User;
import com.sparta.shoppingmall.user.entity.UserStatus;
import com.sparta.shoppingmall.user.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LikesResponseTest {

    private User user;
    private Likes likes;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testUser")
                .password("password")
                .recentPassword("recentPassword")
                .recentPassword2("recentPassword2")
                .recentPassword3("recentPassword3")
                .name("Test User")
                .email("test@example.com")
                .address("123 Test Street")
                .userStatus(UserStatus.JOIN)
                .userType(UserType.ADMIN)
                .statusChangedAt(LocalDateTime.now())
                .products(Collections.emptyList())
                .cart(null)
                .orderGroups(Collections.emptyList())
                .likes(Collections.emptyList())
                .followers(Collections.emptyList())
                .followings(Collections.emptyList())
                .build();

        user.setId(1L);

        likes = Likes.builder()
                .contenttype(ContentType.PRODUCT)
                .contentId(1L)
                .status(LikeStatus.LIKED)
                .user(user)
                .build();
    }

    @Test
    void testLikesResponse() {
        LikesResponse likesResponse = new LikesResponse(likes);

        assertEquals(likes.getId(), likesResponse.getId(), "ID가 일치해야 합니다.");
        assertEquals(likes.getContentType(), likesResponse.getContentType(), "ContentType이 일치해야 합니다.");
        assertEquals(likes.getContentId(), likesResponse.getContentId(), "ContentId가 일치해야 합니다.");
        assertEquals(likes.getStatus(), likesResponse.getStatus(), "Status가 일치해야 합니다.");
    }
}
