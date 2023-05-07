package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.QSubscriptionLike;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.app.happybox.entity.subscript.QSubscriptionLike.subscriptionLike;

@RequiredArgsConstructor
public class SubscriptionLikeQueryDslImpl implements SubscriptionLikeQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public boolean checkMemberLikesSubscription_QueryDSL(Member member, Subscription subscription) {
        Long count = query.select(subscriptionLike.count())
                .from(subscriptionLike)
                .where(subscriptionLike.member.eq(member).and(subscriptionLike.subscription.eq(subscription)))
                .fetchOne();

        return count > 0;
    }
}
