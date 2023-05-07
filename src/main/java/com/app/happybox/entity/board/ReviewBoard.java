package com.app.happybox.entity.board;

import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.Welfare;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_REVIEW_BOARD")
@DynamicInsert
@Getter @ToString(exclude = {"reviewBoardReplies", "reviewBoardLikes"}) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewBoard extends Board {

    /* 리뷰 게시판 정보 */
    @ColumnDefault(value = "5")
    private Integer reviewRating;

    @ColumnDefault(value = "0")
    private Integer reviewLikeCount;
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

    public ReviewBoard(String boardContent, Integer reviewRating, Integer reviewLikeCount, Member member, Subscription subscription) {
        super(boardContent);
        this.reviewRating = reviewRating;
        this.reviewLikeCount = reviewLikeCount;
        this.member = member;
        this.subscription = subscription;
    }
}
