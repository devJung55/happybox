package com.app.happybox.repository.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DonationBoardQueryDslImpl implements DonationBoardQueryDsl {
    private final JPAQueryFactory query;
}
