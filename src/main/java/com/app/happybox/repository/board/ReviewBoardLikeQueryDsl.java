package com.app.happybox.repository.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;

public interface ReviewBoardLikeQueryDsl {
    public boolean checkMemberLikesReviewBoard_QueryDSL(Long userId, Long reviewBoardId);
    public void deleteUserLikeByUserAndReviewBoard(Long userId, Long reviewBoardId);
}
