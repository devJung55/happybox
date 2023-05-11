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
    public boolean checkMemberLikesReviewBoard_QueryDSL(Member member, ReviewBoard reviewboard) {
        Long count = query.select(reviewBoardLike.count())
                .from(reviewBoardLike)
                .where(reviewBoardLike.member.eq(member).and(reviewBoardLike.reviewBoard.eq(reviewboard)))
                .fetchOne();
        return count > 0;
    }
}
