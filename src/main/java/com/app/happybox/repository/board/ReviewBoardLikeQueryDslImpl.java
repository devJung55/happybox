package com.app.happybox.repository.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.app.happybox.entity.board.QReviewBoardLike.reviewBoardLike;


@RequiredArgsConstructor
public class ReviewBoardLikeQueryDslImpl implements ReviewBoardLikeQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public boolean checkMemberLikesReviewBoard_QueryDSL(Long userId, Long reviewBoardId) {
        Long count = query.select(reviewBoardLike.count())
                .from(reviewBoardLike)
                .where(reviewBoardLike.member.id.eq(userId).and(reviewBoardLike.reviewBoard.id.eq(reviewBoardId)))
                .fetchOne();
        return count > 0;
    }
}
