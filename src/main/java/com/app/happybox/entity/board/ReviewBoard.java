package com.app.happybox.entity.board;

import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.Welfare;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_REVIEW_BOARD")
@DynamicInsert
@Getter @ToString(exclude = {"reviewBoardReplies", "reviewBoardLikes"}, callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewBoard extends Board {

    /* 리뷰 게시판 정보 */
    @ColumnDefault(value = "5")
    private Integer reviewRating;

    @ColumnDefault(value = "0")
    private Integer reviewLikeCount;

    @ColumnDefault(value="0")
    private Integer reviewBoardReplyCount;
    /* ============= */

    /* 작성한 유저 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    /* 리뷰할 복지관 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Subscription subscription;

    /* 게시글 댓글 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewBoard", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ReviewBoardReply> reviewBoardReplies = new ArrayList<>();

    /* 게시글 좋아요 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewBoard", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ReviewBoardLike> reviewBoardLikes = new ArrayList<>();

    public ReviewBoard(String boardTitle, String boardContent, Integer reviewRating, Member member, Subscription subscription) {
        super(boardTitle, boardContent);
        this.reviewRating = reviewRating;
        this.member = member;
        this.subscription = subscription;
    }

    @Builder
    public ReviewBoard(String boardTitle, String boardContent, Integer reviewRating) {
        super(boardTitle, boardContent);
        this.reviewRating = reviewRating;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public void setReviewLikeCount(Integer reviewLikeCount) {
        this.reviewLikeCount = reviewLikeCount;
    }

    public void setReviewBoardReplyCount(Integer reviewBoardReplyCount) {
        this.reviewBoardReplyCount = reviewBoardReplyCount;
    }

}
