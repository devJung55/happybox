package com.app.happybox.repository.product;

import com.app.happybox.entity.product.ProductCart;
import com.app.happybox.entity.product.QSubscriptionCart;
import com.app.happybox.entity.product.SubscriptionCart;
import com.app.happybox.entity.subscript.Subscription;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.product.QSubscriptionCart.subscriptionCart;


@RequiredArgsConstructor
public class SubscriptionCartQueryDslImpl implements SubscriptionCartQueryDsl {
    private final JPAQueryFactory query;


    @Override
    public List<SubscriptionCart> findAllByUserId_QueryDSL(Long id) {
        return query.select(subscriptionCart)
                .from(subscriptionCart)
                .join(subscriptionCart.subscription).fetchJoin()
                .where(subscriptionCart.member.id.eq(id))
                .fetch();
    }

    @Override
    public List<SubscriptionCart> findAllByIdsWithDetail_QueryDSL(List<Long> ids) {
        return query.select(subscriptionCart)
                .from(subscriptionCart)
                .join(subscriptionCart.subscription).fetchJoin()
                .where(subscriptionCart.id.in(ids))
                .fetch();
    }

//    구독 Id로 카드 확인
    @Override
    public Optional<Subscription> existCartBySubscriptionId(Long subscriptionId) {
        return Optional.ofNullable(
                query.select(subscriptionCart.subscription)
                .from(subscriptionCart)
                .where(subscriptionCart.subscription.id.eq(subscriptionId))
                .fetchOne()
        );
    }
}
