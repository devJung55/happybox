package com.app.happybox.repository.order;

import com.app.happybox.entity.order.OrderSubscription;
import com.app.happybox.entity.user.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.order.QOrderSubscription.orderSubscription;


@RequiredArgsConstructor
public class OrderSubscriptionQueryDslImpl implements OrderSubscriptionQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Optional<OrderSubscription> findSubscriptionByMemberId_QueryDSL(Long memberId) {
        List<OrderSubscription> subscriptions = query.select(orderSubscription)
                .from(orderSubscription)
                .join(orderSubscription.member).fetchJoin()
                .join(orderSubscription.subscription).fetchJoin()
                .where(orderSubscription.member.id.eq(memberId))
                .fetch();

        if (!subscriptions.isEmpty()) {
            return Optional.of(subscriptions.get(0));
        } else {
            return Optional.empty();
        }
    }


    @Override
    public Long findSubscriptionCountByMemberId_QueryDSL(Long memberId) {
        Long count = query.select(orderSubscription.id.count())
                .from(orderSubscription)
                .where(orderSubscription.member.id.eq(memberId))
                .fetchOne();

        return count;
    }

    @Override
    public Page<OrderSubscription> findSubscriberListByWelfareIdDescWithPaging_QueryDSL(Pageable pageable, Long welfareId, String subscriberName) {
        BooleanExpression subscriberNameContains = subscriberName == null || subscriberName == "" ? null : orderSubscription.member.memberName.contains(subscriberName);

        List<OrderSubscription> orderSubscriptionList = query.select(orderSubscription)
                .from(orderSubscription)
                .join(orderSubscription.member).fetchJoin()
                .where(orderSubscription.subscription.welfare.id.eq(welfareId))
                .where(subscriberNameContains)
                .orderBy(orderSubscription.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(orderSubscription.id.count()).from(orderSubscription).where(orderSubscription.subscription.welfare.id.eq(welfareId)).fetchOne();

        return new PageImpl<>(orderSubscriptionList, pageable, count);
    }

    @Override
    public Long findSubscriberCountByWelfareId_QueryDSL(Long welfareId) {
        Long count = query.select(orderSubscription.member.id.count())
                .from(orderSubscription)
                .where(orderSubscription.subscription.welfare.id.eq(welfareId))
                .fetchOne();

        return count;
    }

    @Override
    public Page<Member> findAllMembersByWelfareId(Pageable pageable, Long welfareId, String subscriberName) {
        BooleanExpression subscriberNameContains = subscriberName == null || subscriberName == "" ? null : orderSubscription.member.memberName.contains(subscriberName);

        List<Member> memberList = query.select(orderSubscription.member)
                .from(orderSubscription)
                .where(orderSubscription.subscription.welfare.id.eq(welfareId))
                .where(subscriberNameContains)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(orderSubscription.member.count())
                .from(orderSubscription)
                .where(orderSubscription.subscription.welfare.id.eq(welfareId))
                .fetchOne();

        return new PageImpl<>(memberList, pageable, count);
    }
}
