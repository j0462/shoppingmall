package com.sparta.shoppingmall.service;

import com.sparta.shoppingmall.comment.service.CommentService;
import com.sparta.shoppingmall.like.dto.LikesRequest;
import com.sparta.shoppingmall.like.dto.LikesResponse;
import com.sparta.shoppingmall.like.entity.ContentType;
import com.sparta.shoppingmall.like.entity.LikeStatus;
import com.sparta.shoppingmall.like.entity.Likes;
import com.sparta.shoppingmall.like.repository.LikesRepository;
import com.sparta.shoppingmall.like.service.LikesService;
import com.sparta.shoppingmall.product.controller.entity.Product;
import com.sparta.shoppingmall.product.controller.entity.ProductStatus;
import com.sparta.shoppingmall.product.service.ProductService;
import com.sparta.shoppingmall.user.entity.User;
import com.sparta.shoppingmall.user.entity.UserStatus;
import com.sparta.shoppingmall.user.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LikesServiceTest {

    @Mock
    private ProductService productService;

    @Mock
    private CommentService commentService;

    @Mock
    private LikesRepository likesRepository;

    @InjectMocks
    private LikesService likesService;

    private User user;
    private Product product;

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

        product = Product.builder()
                .user(user)
                .name("TestProduct")
                .price(1000L)
                .status(ProductStatus.ONSALE)
                .build();
        product.setId(1L);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toggleLike_NewLike() {
        LikesRequest request = new LikesRequest(ContentType.PRODUCT, 1L);

        when(likesRepository.findByContentTypeAndContentId(request.getContentType(), request.getContentId()))
                .thenReturn(Optional.empty());

        Likes likes = new Likes(request, user);
        when(likesRepository.save(any(Likes.class))).thenReturn(likes);

        when(productService.findByProductId(request.getContentId())).thenReturn(product);

        LikesResponse response = likesService.toggleLike(request, user);

        assertEquals(LikeStatus.LIKED, response.getStatus());
        verify(likesRepository, times(1)).save(any(Likes.class));
    }

    @Test
    void toggleLike_CancelLike() {
        LikesRequest request = new LikesRequest(ContentType.PRODUCT, 1L);

        Likes existingLike = new Likes(request.getContentType(), request.getContentId(), LikeStatus.LIKED, user);

        when(likesRepository.findByContentTypeAndContentId(request.getContentType(), request.getContentId()))
                .thenReturn(Optional.of(existingLike));

        when(productService.findByProductId(request.getContentId())).thenReturn(product);

        LikesResponse response = likesService.toggleLike(request, user);

        assertEquals(LikeStatus.CANCELED, response.getStatus());
        verify(likesRepository, times(0)).save(any(Likes.class));
    }
}
