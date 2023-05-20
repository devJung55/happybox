package com.app.happybox.entity.board;

import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.Welfare;
import com.sun.istack.NotNull;
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

    @NotNull
    private String welfareName;
    /* ============= */

    /* 작성한 유저 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

//    /* 리뷰할 복지관 */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn
//    private Welfare welfare;

    /* 게시글 댓글 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewBoard", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ReviewBoardReply> reviewBoardReplies = new ArrayList<>();

    /* 게시글 좋아요 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewBoard", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ReviewBoardLike> reviewBoardLikes = new ArrayList<>();

    /* 리뷰 파일 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewBoard", orphanRemoval = true)
    private List<BoardFile> reviewBoardFiles = new ArrayList<>();

    @Builder
    public ReviewBoard(Long id, String boardTitle, String boardContent, List<BoardFile> reviewBoardFiles, Integer reviewRating, Integer reviewLikeCount, Integer reviewBoardReplyCount, String welfareName) {
        super(id, boardTitle, boardContent, reviewBoardFiles);
        this.reviewRating = reviewRating;
        this.reviewLikeCount = reviewLikeCount;
        this.reviewBoardReplyCount = reviewBoardReplyCount;
        this.welfareName = welfareName;
    }

    //    public ReviewBoard(String boardTitle, String boardContent, Integer reviewRating, Member member, Welfare welfare) {
//        super(boardTitle, boardContent);
//        this.reviewRating = reviewRating;
//        this.member = member;
//        this.welfare = welfare;
//    }
//
//    @Builder
//    public ReviewBoard(Long id, String boardTitle, String boardContent, List<BoardFile> boardFiles, Integer reviewRating, Integer reviewLikeCount, Integer reviewBoardReplyCount, Member member, Welfare welfare, List<ReviewBoardReply> reviewBoardReplies, List<ReviewBoardLike> reviewBoardLikes) {
//        super(id, boardTitle, boardContent, boardFiles);
//        this.reviewRating = reviewRating;
//        this.reviewLikeCount = reviewLikeCount;
//        this.reviewBoardReplyCount = reviewBoardReplyCount;
//        this.member = member;
//        this.welfare = welfare;
//        this.reviewBoardReplies = reviewBoardReplies;
//        this.reviewBoardLikes = reviewBoardLikes;
//    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setWelfareName(String welfareName) {
        this.welfareName = welfareName;
    }

    public void setReviewRating(Integer reviewRating) {
        this.reviewRating = reviewRating;
    }

    public void setReviewLikeCount(Integer reviewLikeCount) {
        this.reviewLikeCount = reviewLikeCount;
    }

    public void setReviewBoardReplyCount(Integer reviewBoardReplyCount) {
        this.reviewBoardReplyCount = reviewBoardReplyCount;
    }

}
