package com.app.happybox.repository.board;

import com.app.happybox.entity.board.QReviewBoard;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.user.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.app.happybox.entity.board.QReviewBoard.reviewBoard;

@RequiredArgsConstructor
public class ReviewBoardQueryDslImpl implements ReviewBoardQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<ReviewBoard> findReviewBoardListByMemberIdWithPaging_QueryDSL(Member member) {
        List<ReviewBoard> reviewBoardList =  query.select(reviewBoard)
                .from(reviewBoard)
                .where(reviewBoard.member.eq(member))
                .fetch();

        return reviewBoardList;
    }
}
