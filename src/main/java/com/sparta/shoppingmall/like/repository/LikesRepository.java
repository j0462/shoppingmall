package com.sparta.shoppingmall.like.repository;

import com.sparta.shoppingmall.like.entity.ContentType;
import com.sparta.shoppingmall.like.entity.Likes;
import com.sparta.shoppingmall.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByContentTypeAndContentId(ContentType contentType, Long contentId);
    Page<Likes> findByUser(User user, Pageable pageable);
    Page<Likes> findByUserAndContentType(User user, ContentType contentType, Pageable pageable);
}
