package com.app.happybox.repository.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberOrderProductQueryDslImpl implements MemberOrderProductQueryDsl {
    private final JPAQueryFactory query;

}
