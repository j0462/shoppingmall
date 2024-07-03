package com.sparta.shoppingmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.shoppingmall.base.dto.CommonResponse;
import com.sparta.shoppingmall.product.controller.ProductController;
import com.sparta.shoppingmall.product.controller.entity.ProductStatus;
import com.sparta.shoppingmall.product.dto.ProductResponse;
import com.sparta.shoppingmall.product.service.ProductService;
import com.sparta.shoppingmall.security.UserDetailsImpl;
import com.sparta.shoppingmall.user.entity.User;
import com.sparta.shoppingmall.user.entity.UserStatus;
import com.sparta.shoppingmall.user.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @Mock
    private UserDetailsImpl userDetails;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
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

        objectMapper = new ObjectMapper();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "password", userDetails.getAuthorities()));
    }

    @Test
    void getLikedProducts() throws Exception {
        ProductResponse product1 = new ProductResponse(1L, "Product 1", 1L, ProductStatus.ONSALE, user, 1);
        ProductResponse product2 = new ProductResponse(2L, "Product 2", 1L, ProductStatus.ONSALE, user, 1);

        List<ProductResponse> likedProducts = List.of(product1, product2);
        Mockito.when(productService.getLikedProducts(user, 0)).thenReturn(likedProducts);


        CommonResponse expectedResponse = CommonResponse.builder()
                .statusCode(200)
                .message("좋아요 한 상품 목록 조회 성공")
                .data(likedProducts)
                .build();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedResponse);

        this.mockMvc.perform(get("/api/products/liked")
                        .param("page", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }
}
