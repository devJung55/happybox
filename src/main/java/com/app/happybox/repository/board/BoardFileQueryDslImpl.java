package com.app.happybox.repository.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardFileQueryDslImpl implements BoardFileQueryDsl {
    private final JPAQueryFactory query;
}
