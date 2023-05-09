package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Food;
import com.app.happybox.entity.subscript.Subscription;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.app.happybox.entity.subscript.QFood.food;
import static com.app.happybox.entity.subscript.QSubscription.subscription;

@RequiredArgsConstructor
public class SubscriptionQueryDslImpl implements SubscriptionQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<Subscription> findTop8OrderByDate_QueryDSL() {

        List<Subscription> subscriptionList = getSubscriptionJPAQuery()
                .orderBy(subscription.createdDate.desc())
                .limit(8L)
                .fetch();

        return subscriptionList;
    }

    @Override
    public List<Subscription> findTop3BetweenDateOrderByDateDesc_QueryDSL(LocalDateTime startDate, LocalDateTime endDate) {
        List<Subscription> subscriptionList = getSubscriptionJPAQuery()
                .where(subscription.createdDate.between(startDate, endDate))
                .orderBy(subscription.createdDate.desc())
                .limit(3L)
                .fetch();

        return subscriptionList;
    }

    @Override
    public List<Subscription> findTop4OrderByReviewCount_QueryDSL() {
        List<Subscription> subscriptionList = getSubscriptionJPAQuery()
                .orderBy(subscription.reviewCount.desc())
                .limit(4L)
                .fetch();
        return subscriptionList;
    }

    @Override
    public Page<Subscription> findAllByAddressCategoryWithPaging_QueryDSL(Pageable pageable, String address) {
        //    해당 지역 주소를 포함하고 있는가? ex) 강남 in 서울시 '강남구' ...
        BooleanExpression hasAddress = address == null ? null : subscription.welfare.address.firstAddress.contains(address);

        List<Subscription> subscriptionList = getSubscriptionJPAQuery()
                .from(subscription)
                .where(hasAddress)
                .orderBy(subscription.reviewBoards.size().asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(subscription.count())
                .from(subscription)
                .where(hasAddress).fetchOne();

        return new PageImpl<>(subscriptionList, pageable, count);
    }

    @Override
    public Subscription findByIdWithDetail_QueryDSL(Long id) {
        Subscription sub = getSubscriptionJPAQuery()
                .where(subscription.id.eq(id))
                .fetchOne();

        return sub;
    }

    /* 중복 코드인 Subscription JPAQuery 리턴 */
    private JPAQuery<Subscription> getSubscriptionJPAQuery() {
        return query.select(subscription)
                .from(subscription)
                .join(subscription.foodCalendars).fetchJoin()
                .join(subscription.welfare).fetchJoin();
    }
}
