package com.app.happybox.repository.reply;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductReplyQueryDslImpl implements ProductReplyQueryDsl {
    private final JPAQueryFactory query;
}
