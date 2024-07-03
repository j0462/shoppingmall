package com.sparta.shoppingmall.controller;

import com.sparta.shoppingmall.security.UserDetailsImpl;
import com.sparta.shoppingmall.user.controller.UserController;
import com.sparta.shoppingmall.user.dto.EditProfileResponseDTO;
import com.sparta.shoppingmall.user.entity.User;
import com.sparta.shoppingmall.user.entity.UserStatus;
import com.sparta.shoppingmall.user.entity.UserType;
import com.sparta.shoppingmall.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

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

        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "password", userDetails.getAuthorities()));
    }

    @Test
    void userProfile2_Success() throws Exception {
        EditProfileResponseDTO responseDTO = new EditProfileResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAddress(),
                5,
                3
        );

        Mockito.when(userService.inquiryUser2(anyLong(), any(User.class))).thenReturn(responseDTO);

        this.mockMvc.perform(get("/api/users/{userId}/profiles2", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("프로필 조회 성공"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("testuser"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"))
                .andExpect(jsonPath("$.data.address").value("123 Test St"))
                .andExpect(jsonPath("$.data.likedProductsCount").value(5))
                .andExpect(jsonPath("$.data.likedCommentsCount").value(3))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void userProfile2_UserNotFound() throws Exception {
        Mockito.when(userService.inquiryUser2(anyLong(), any(User.class))).thenThrow(new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        this.mockMvc.perform(get("/api/users/{userId}/profiles2", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("사용자를 찾을 수 없습니다."))
                .andDo(print());
    }
}
