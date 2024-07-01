package com.sparta.shoppingmall.product.repository;

import com.sparta.shoppingmall.product.controller.entity.Product;
import com.sparta.shoppingmall.product.controller.entity.ProductStatus;
import com.sparta.shoppingmall.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findAllByStatusInOrderByCreateAtDescStatusAsc(List<ProductStatus> condi, Pageable pageable);
}
