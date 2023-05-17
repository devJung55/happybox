package com.app.happybox.entity.reply;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "TBL_REVIEW_BOARD_REPLY")
@DynamicInsert
@DiscriminatorValue("REVIEW_BOARD")
@Getter @ToString(callSuper = true, exclude = "reviewBoard") @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewBoardReply extends Reply {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ReviewBoard reviewBoard;

    @Builder
    public ReviewBoardReply(String replyContent, User user, ReviewBoard reviewBoard) {
        super(replyContent, user);
        this.reviewBoard = reviewBoard;
    }
}
