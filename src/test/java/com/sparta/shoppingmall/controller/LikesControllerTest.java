package com.sparta.shoppingmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.shoppingmall.comment.entity.Comment;
import com.sparta.shoppingmall.like.controller.LikesController;
import com.sparta.shoppingmall.like.dto.LikesRequest;
import com.sparta.shoppingmall.like.dto.LikesResponse;
import com.sparta.shoppingmall.like.entity.ContentType;
import com.sparta.shoppingmall.like.entity.LikeStatus;
import com.sparta.shoppingmall.like.entity.Likes;
import com.sparta.shoppingmall.like.service.LikesService;
import com.sparta.shoppingmall.product.controller.entity.Product;
import com.sparta.shoppingmall.product.controller.entity.ProductStatus;
import com.sparta.shoppingmall.security.UserDetailsImpl;
import com.sparta.shoppingmall.user.entity.User;
import com.sparta.shoppingmall.user.entity.UserStatus;
import com.sparta.shoppingmall.user.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikesController.class)
class LikesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LikesService likesService;

    private User user;
    private Likes likes;
    private Product product;
    private Comment comment;

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

        product = Product.builder()
                .user(user)
                .name("TestProduct")
                .price(1000L)
                .status(ProductStatus.ONSALE)
                .build();
        product.setId(1L);

        comment = Comment.builder()
                .id(1L)
                .content("Test")
                .likeCount(0)
                .product(product)
                .user(user)
                .build();

        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "password", userDetails.getAuthorities()));
    }

    @Test
    void testToggleProductLike() throws Exception {
        LikesRequest request = new LikesRequest(ContentType.PRODUCT, 1L);
        LikesResponse response = new LikesResponse(likes);

        Mockito.when(likesService.toggleLike(any(LikesRequest.class), any(User.class)))
                .thenReturn(response);

        this.mockMvc.perform(post("/api/products/1/like")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testToggleCommentLike() throws Exception {
        LikesRequest request = new LikesRequest(ContentType.COMMENT, 1L);
        LikesResponse response = new LikesResponse(likes);

        Mockito.when(likesService.toggleLike(any(LikesRequest.class), any(User.class)))
                .thenReturn(response);

        this.mockMvc.perform(post("/api/comments/1/like")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testToggleProductLikeWithValidationError() throws Exception {
        LikesRequest request = new LikesRequest(ContentType.PRODUCT, null);

        this.mockMvc.perform(post("/api/products/1/like")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testToggleCommentLikeWithValidationError() throws Exception {
        LikesRequest request = new LikesRequest(null, 1L);

        this.mockMvc.perform(post("/api/comments/1/like")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
