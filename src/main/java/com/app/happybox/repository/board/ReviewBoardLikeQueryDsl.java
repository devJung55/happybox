package com.app.happybox.repository.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;

public interface ReviewBoardLikeQueryDsl {
    public boolean checkMemberLikesReviewBoard_QueryDSL(Member member, ReviewBoard reviewboard);
}
