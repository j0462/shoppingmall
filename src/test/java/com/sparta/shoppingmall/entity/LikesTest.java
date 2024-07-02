package com.sparta.shoppingmall.entity;

import com.sparta.shoppingmall.like.dto.LikesRequest;
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

import static org.junit.jupiter.api.Assertions.*;

class LikesTest {

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

        user.setId(8L);
        LikesRequest likesRequest = new LikesRequest(ContentType.PRODUCT, 1L);

        likes = new Likes(likesRequest, user);
    }

    @Test
    void testDoLike() {
        likes.doLike(user.getId());
        assertEquals(LikeStatus.LIKED, likes.getStatus(), "좋아요 상태가 LIKED여야 합니다.");
    }

    @Test
    void testCancelLike() {
        likes.doLike(user.getId());
        likes.cancelLike(user.getId());
        assertEquals(LikeStatus.CANCELED, likes.getStatus(), "좋아요 상태가 CANCELED여야 합니다.");
    }

    @Test
    void testVerifyUser() {
        assertThrows(IllegalArgumentException.class, () -> likes.verifyUser(2L), "사용자가 일치하지 않을 때 예외가 발생해야 합니다.");
    }
}
