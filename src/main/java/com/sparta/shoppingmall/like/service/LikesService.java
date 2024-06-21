package com.sparta.shoppingmall.like.service;

import com.sparta.shoppingmall.like.dto.LikesRequest;
import com.sparta.shoppingmall.like.dto.LikesResponse;
import com.sparta.shoppingmall.like.entity.LikeStatus;
import com.sparta.shoppingmall.like.entity.Likes;
import com.sparta.shoppingmall.like.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;

    /**
     * 좋아요 토글
     */
    @Transactional
    public LikesResponse toggleLike(LikesRequest request) {
        Optional<Likes> existLike = likesRepository.findByContentTypeAndContentId(request.getContentType(), request.getContentId());

        Likes likes = existLike.orElseGet(() -> createLikes(request/*, user*/));

        if (likes.getStatus().equals(LikeStatus.CANCELED)) {
            doLike(likes/*, user.getId()*/);// 취소된 좋아요 이거나 신규 좋아요인 경우 좋아요
        }else {
            cancelLike(likes/*, user.getId()*/);
        }

        return LikesResponse.builder()
                .build();
                //.id(like.getId())
                //.contentType(like.getContentType())
                //.contentId(like.getContentId())
                //.status(like.getStatus())

    }

    /**
     * 좋아요 생성 (해당 content에 최초 좋아요 실행일 때)
     */
    private Likes createLikes(LikesRequest request/*, User user*/) {
        Likes likes = new Likes(request/*, user*/);
        likesRepository.save(likes);

        return likes;
    }

    /**
     * 좋아요
     */
    private void doLike(Likes likes/*, Long userId*/) {
        //likes.doLike(userId);

        Long contentId = likes.getContentId();
        //switch (likes.getContentType()){
        //    case PRODUCT -> productService.getProduct().increaseLikeCount();
        //    case COMMENT -> productService.getComment().increaseLikeCount();
        //}
    }

    /**
     * 좋아요 취소
     */
    private void cancelLike(Likes likes/*, Long userId*/) {
        //likes.doLike(userId);

        Long contentId = likes.getContentId();
        //switch (likes.getContentType()){
        //    case PRODUCT -> productService.getProduct().decreaseLikeCount();
        //    case COMMENT -> productService.getComment().decreaseLikeCount();
        //}
    }




}
