package com.app.happybox.entity.reply;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "TBL_REVIEW_BOARD_REPLY")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewBoardReply extends Reply {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ReviewBoard reviewBoard;

    public ReviewBoardReply(String replyContent, User user, ReviewBoard reviewBoard) {
        super(replyContent, user);
        this.reviewBoard = reviewBoard;
    }
}
