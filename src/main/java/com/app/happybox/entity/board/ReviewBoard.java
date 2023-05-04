package com.app.happybox.entity.board;

import com.app.happybox.entity.reply.BoardReply;
import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.user.Welfare;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "TBL_REVIEW_BOARD")
@DynamicInsert
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewBoard extends Board {

    /* 리뷰 게시판 정보 */
    @ColumnDefault(value = "5")
    private Integer reviewRating;

    @ColumnDefault(value = "0")
    private Integer reviewLikeCount;
    /* ============= */

    /* 리뷰할 복지관 */
    @OneToOne(fetch = FetchType.LAZY)
    private Welfare welfare;

    /* 게시글 댓글 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<BoardReply> boardReplies;

    /* 게시글 좋아요 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<BoardLike> boardLikes;
}
