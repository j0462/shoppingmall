package com.sparta.shoppingmall.like.entity;

import com.sparta.shoppingmall.base.entity.Timestamped;
import com.sparta.shoppingmall.like.dto.LikesRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "content_type")
    @Enumerated(EnumType.STRING)
    private ContentType contentType; // 컨텐츠 타입 [BOARD, COMMENT]

    @Column(nullable = false, name = "content_id")
    private Long contentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LikeStatus status; // 좋아요 상태 [LIKED, CANCELED]

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id")
    //private User user;

    @Builder
    public Likes(ContentType contenttype, Long contentId, LikeStatus status/*, User user*/) {
        this.contentType = contenttype;
        this.contentId = contentId;
        this.status = status;
        //this.user = user;
    }

    /**
     * 생성자
     */
    public Likes(LikesRequest likesRequest/*, User user*/){
        this.contentType = likesRequest.getContentType();
        this.contentId = likesRequest.getContentId();
        this.status = LikeStatus.CANCELED;
    }

    /**
     * 좋아요 메서드
     */
    public void doLike(Long userId) {
        //this.verifyUser(userId);
        this.status = LikeStatus.LIKED;
    }

    /**
     * 좋아요 취소 메서드
     */
    public void cancelLike(Long userId) {
        //this.verifyUser(userId);
        this.status = LikeStatus.CANCELED;
    }

    /**
     * 사용자 검증 메서드
     */
    //public void verifyUser(Long userId) {
    //    if (!userId.equals(this.user.getId())) {
    //        throw new IllegalArgumentException("사용자가 일치 하지 않습니다.");
    //    }
    //}

}
