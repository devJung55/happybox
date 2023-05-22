package com.app.happybox.entity.board;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_REVIEW_BOARD_LIKE")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewBoardLike extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ReviewBoard reviewBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    public ReviewBoardLike(ReviewBoard reviewBoard, Member member) {
        this.reviewBoard = reviewBoard;
        this.member = member;
    }
}
