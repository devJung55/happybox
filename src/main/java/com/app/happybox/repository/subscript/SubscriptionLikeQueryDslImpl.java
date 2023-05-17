package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.SubscriptionLike;
import com.app.happybox.entity.user.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    @Override
    public void deleteUserLikeByUserAndSubscription(Member member, Subscription subscription) {
        query.delete(subscriptionLike)
                .where(subscriptionLike.member.eq(member).and(subscriptionLike.subscription.eq(subscription)))
                .execute();
    }

    @Override
    public Page<SubscriptionLike> findSubscriptionBookmarkByMemberIdWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        List<SubscriptionLike> subscriptionLikeList = query.select(subscriptionLike)
                .from(subscriptionLike)
                .where(subscriptionLike.member.id.eq(memberId))
                .orderBy(subscriptionLike.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(subscriptionLike.id.count())
                .from(subscriptionLike)
                .where(subscriptionLike.member.id.eq(memberId))
                .fetchOne();

        return new PageImpl<>(subscriptionLikeList, pageable, count);
    }


}
