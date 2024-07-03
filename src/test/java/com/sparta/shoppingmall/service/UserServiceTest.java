package com.sparta.shoppingmall.service;

import com.sparta.shoppingmall.like.entity.ContentType;
import com.sparta.shoppingmall.like.repository.LikesRepository;
import com.sparta.shoppingmall.user.dto.EditProfileResponseDTO;
import com.sparta.shoppingmall.user.entity.User;
import com.sparta.shoppingmall.user.entity.UserStatus;
import com.sparta.shoppingmall.user.entity.UserType;
import com.sparta.shoppingmall.user.repository.UserRepository;
import com.sparta.shoppingmall.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LikesRepository likesRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testuser")
                .password("password")
                .recentPassword("recentPassword")
                .recentPassword2("recentPassword2")
                .recentPassword3("recentPassword3")
                .name("Test User")
                .email("test@example.com")
                .address("123 Test St")
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

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void inquiryUser2_ValidUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(likesRepository.countByUserAndContentType(user, ContentType.PRODUCT)).thenReturn(5);
        when(likesRepository.countByUserAndContentType(user, ContentType.COMMENT)).thenReturn(3);

        EditProfileResponseDTO response = userService.inquiryUser2(1L, user);

        assertEquals(1L, response.getId());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("123 Test St", response.getAddress());
        assertEquals(5, response.getLikedProductsCount());
        assertEquals(3, response.getLikedCommentsCount());
    }

    @Test
    void inquiryUser2_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            userService.inquiryUser2(1L, user);
        });
    }
}
