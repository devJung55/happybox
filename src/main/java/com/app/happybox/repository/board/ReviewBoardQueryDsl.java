package com.app.happybox.repository.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.user.Member;

import java.util.List;

public interface ReviewBoardQueryDsl {
//    나의후기 목록 페이징
    public List<ReviewBoard> findReviewBoardListByMemberIdWithPaging_QueryDSL(Member member);

}
