package com.app.happybox.repository.order;

import com.app.happybox.entity.order.OrderSubscription;
import com.app.happybox.entity.order.QOrderSubscription;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.app.happybox.entity.order.QOrderSubscription.orderSubscription;

@RequiredArgsConstructor
public class OrderSubscriptionQueryDslImpl implements OrderSubscriptionQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Optional<OrderSubscription> findSubscriptionByMemberId_QueryDSL(Long memberId) {
        return Optional.ofNullable(query.select(orderSubscription)
                .from(orderSubscription)
                .join(orderSubscription.member).fetchJoin()
                .join(orderSubscription.subscription).fetchJoin()
                .where(orderSubscription.member.id.eq(memberId))
                .fetchOne());
    }
}
