package com.app.happybox.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DistributorQueryDslImpl implements DistributorQueryDsl {
    private final JPAQueryFactory query;
}
