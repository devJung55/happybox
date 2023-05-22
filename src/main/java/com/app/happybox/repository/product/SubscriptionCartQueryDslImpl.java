package com.app.happybox.repository.product;

import com.app.happybox.entity.product.ProductCart;
import com.app.happybox.entity.product.QSubscriptionCart;
import com.app.happybox.entity.product.SubscriptionCart;
import com.app.happybox.entity.subscript.Subscription;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
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
    public Optional<SubscriptionCart> existCartByMemberIdAndSubscriptionId(Long memberId, Long subscriptionId) {
        return Optional.ofNullable(
                query.select(subscriptionCart)
                        .from(subscriptionCart)
                        .where(subscriptionCart.member.id.eq(memberId), subscriptionCart.subscription.id.eq(subscriptionId))
                        .fetchOne()
        );
    }

    //    memberId로 cart 삭제하기
    @Override
    @Transactional
    public void deleteCart(Long id) {
        query.delete(subscriptionCart)
                .where(subscriptionCart.member.id.eq(id))
                .execute();
    }
}
