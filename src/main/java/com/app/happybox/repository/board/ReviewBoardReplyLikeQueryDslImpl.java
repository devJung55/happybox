package com.app.happybox.repository.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewBoardReplyLikeQueryDslImpl implements ReviewBoardReplyLikeQueryDsl {
    private final JPAQueryFactory query;
}
