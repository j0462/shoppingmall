package com.sparta.shoppingmall.product.repository;

import com.sparta.shoppingmall.product.entity.Product;
import com.sparta.shoppingmall.product.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAllByStatus(Pageable pageable, ProductStatus status);
}
