package com.app.happybox.repository.payment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentQueryDslImpl implements PaymentQueryDsl {
    private final JPAQueryFactory query;


}
