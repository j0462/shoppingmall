package com.sparta.shoppingmall.comment.service;

import com.sparta.shoppingmall.comment.dto.CommentRequest;
import com.sparta.shoppingmall.comment.dto.CommentResponse;
import com.sparta.shoppingmall.comment.entity.Comment;
import com.sparta.shoppingmall.comment.repository.CommentRepository;
import com.sparta.shoppingmall.like.entity.ContentType;
import com.sparta.shoppingmall.like.repository.LikesRepository;
import com.sparta.shoppingmall.product.controller.entity.Product;
import com.sparta.shoppingmall.product.service.ProductService;
import com.sparta.shoppingmall.user.entity.User;
import com.sparta.shoppingmall.user.entity.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ProductService productService;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;

    /**
     * 댓글 등록
     */
    @Transactional
    public CommentResponse createComment(CommentRequest request, Long productId, User user) {
        Product product = productService.findByProductId(productId);
        Comment comment = new Comment(request, user, product);

        commentRepository.save(comment);

        return new CommentResponse(comment);
    }

    /**
     * 해당 상품의 댓글 전체 조회
     */
    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long productId) {
        Product product = productService.findByProductId(productId);
        List<Comment> comments = product.getComments();

        List<CommentResponse> response = new ArrayList<>();
        for(Comment comment : comments) {
            response.add(new CommentResponse(comment));
        }

        return response;
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public CommentResponse updateComments(CommentRequest request, Long productId, Long commentId, User user) {
        Comment comment = getComment(commentId);

        if(!user.getUserType().equals(UserType.ADMIN)){ // 관리자가 아닐 경우 댓글 작성자와 로그인 사용자를 비교
            comment.verifyCommentUser(user.getId());
        }
        comment.verifyCommentProduct(productId);
        comment.updateComment(request);

        return new CommentResponse(comment);
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public Long deleteComment(Long productId, Long commentId, User user) {
        Comment comment = getComment(commentId);

        if(!user.getUserType().equals(UserType.ADMIN)){ // 관리자가 아닐 경우 댓글 작성자와 로그인 사용자를 비교
            comment.verifyCommentUser(user.getId());
        }
        comment.verifyCommentProduct(productId);
        commentRepository.delete(comment);

        return comment.getId();
    }

    /**
     * 댓글 아이디로 댓글 조회
     */
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
        );
    }

    public List<CommentResponse> getLikedComments(User user, int page) {
        Pageable pageable = PageRequest.of(page, 5); // 페이지 당 5개의 데이터
        return likesRepository.findByUserAndContentType(user, ContentType.COMMENT, pageable)
                .stream()
                .map(likes -> {
                    Comment comment = commentRepository.findById(likes.getContentId()).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
                    return new CommentResponse(comment);
                })
                .collect(Collectors.toList());
    }
}
