package com.sparta.shoppingmall.service;

import com.sparta.shoppingmall.like.entity.ContentType;
import com.sparta.shoppingmall.like.entity.LikeStatus;
import com.sparta.shoppingmall.like.entity.Likes;
import com.sparta.shoppingmall.like.repository.LikesRepository;
import com.sparta.shoppingmall.product.controller.entity.Product;
import com.sparta.shoppingmall.product.controller.entity.ProductStatus;
import com.sparta.shoppingmall.product.dto.ProductResponse;
import com.sparta.shoppingmall.product.service.ProductService;
import com.sparta.shoppingmall.user.entity.User;
import com.sparta.shoppingmall.user.entity.UserStatus;
import com.sparta.shoppingmall.user.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private LikesRepository likesRepository;

    @InjectMocks
    private ProductService productService;

    private User user;
    private Product product1;
    private Product product2;
    private Likes like1;
    private Likes like2;

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

        product1 = Product.builder()
                .user(user)
                .name("Product 1")
                .price(1000L)
                .status(ProductStatus.ONSALE)
                .build();
        product1.setId(1L);

        product2 = Product.builder()
                .user(user)
                .name("Product 2")
                .price(2000L)
                .status(ProductStatus.ONSALE)
                .build();
        product2.setId(2L);

        like1 = Likes.builder()
                .contenttype(ContentType.PRODUCT)
                .contentId(product1.getId())
                .status(LikeStatus.LIKED)
                .user(user)
                .build();
        like1.setPrduct(product1);

        like2 = Likes.builder()
                .contenttype(ContentType.PRODUCT)
                .contentId(product2.getId())
                .status(LikeStatus.LIKED)
                .user(user)
                .build();
        like2.setPrduct(product2);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLikedProducts() {
        List<Likes> likesList = List.of(like1, like2);

        Pageable pageable = PageRequest.of(0, 5);
        when(likesRepository.findByUser(user, pageable)).thenReturn(new PageImpl<>(likesList));

        List<ProductResponse> responses = productService.getLikedProducts(user, 0);

        assertEquals(2, responses.size());
        assertEquals(1L, responses.get(0).getId());
        assertEquals("Product 1", responses.get(0).getName());
        assertEquals(2L, responses.get(1).getId());
        assertEquals("Product 2", responses.get(1).getName());
    }
}
