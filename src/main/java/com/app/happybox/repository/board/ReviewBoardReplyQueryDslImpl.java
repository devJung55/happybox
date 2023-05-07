package com.app.happybox.repository.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewBoardReplyQueryDslImpl implements ReviewBoardReplyQueryDsl {
    private final JPAQueryFactory query;
}
