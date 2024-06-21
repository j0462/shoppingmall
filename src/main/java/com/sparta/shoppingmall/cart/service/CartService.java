package com.sparta.shoppingmall.cart.service;

import com.sparta.shoppingmall.cart.dto.CartProductRequest;
import com.sparta.shoppingmall.cart.dto.CartProductResponse;
import com.sparta.shoppingmall.cart.dto.CartResponse;
import com.sparta.shoppingmall.cart.entity.CartProduct;
import com.sparta.shoppingmall.cart.repository.CartProductRepository;
import com.sparta.shoppingmall.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    //private final ProductService productService;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

//    public User getUser(Long id) {
//        return userRepository.findById(id).orElseThrow(
//                () -> IllegalArgumentException("userId가" + id + " 인 사용자가 존재하지 않습니다.")
//        );
//    }

    /**
     * 장바구니에 상품 단건 담기
     */
    @Transactional
    public CartProductResponse addCartProduct(CartProductRequest cartProductRequest/*, Long userId*/) {

        //userId 체크
        //User user = getUser(userId);
        //userId로 cart불러오기 로직 추가
        //Cart cart = user.getCart();

        //상품 상태 체크 로직 추가
        //productService.checkProductStatus(cartProductRequest.getProductId()) -> return type void
        //CartProduct cartProduct = createCartProduct(cart/*, product*/);
        //cart.addCartProduct(cartProduct);
        //cartRepository.save(cart);

        return CartProductResponse.builder()
                //.id(cartProduct.getId())
                //.cart(cart)
                //.product(product)
                .build();
    }

    /**
     * 장바구니에 상품 리스트 조회
     */
    @Transactional(readOnly = true)
    public CartResponse getCartProducts(Pageable pageable/*, Long userId*/) {
        //user 체크
        //User user = getUser(userId);
        //user에서 cart불러오기
        //Cart cart = User.getCart();

        //Page<CartProduct> cartProducts = cartProductRepository.findAllByCartId(cart.getId(), pageable);
        //장바구니 조회시 상품 상태 체크 후 삭제하는 메서드 추가.
        //추후 리팩토링 추가로 진행

        return CartResponse.builder()
                //cartId(cart.getId())
                //.cartProducts(cartProducts)
                .build();
    }

    /**
     * 장바구니에 상품 단건 삭제
     */
    @Transactional
    public Long deleteCartProduct(Long productId) {
        //user 체크
        //User user = getUser(userId);
        //user에서 cart불러오기
        //Cart cart = User.getCart();

        CartProduct cartProduct = cartProductRepository.findByProductId(productId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 장바구니에 없습니다.")
        );

        //cart.removeCartProduct(cartProduct);

        return cartProduct.getId();
    }
}
