package com.app.happybox.repository.subscript;

import com.app.happybox.domain.SubscriptionSearchDTO;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.subscript.QSubscription.subscription;
import static com.app.happybox.entity.user.QMember.member;


@RequiredArgsConstructor
@Slf4j
public class SubscriptionQueryDslImpl implements SubscriptionQueryDsl {
    private final JPAQueryFactory query;

    //    최신 8개 조회
    @Override
    public List<Subscription> findTop8OrderByDate_QueryDSL() {
        List<Subscription> subscriptionList = getSubscriptionJPAQuery()/*query.select(subscription).from(subscription)*/
                .orderBy(subscription.createdDate.desc())
                .limit(8L)
                .fetch();
        return subscriptionList;
    }

    @Override
    public List<Subscription> findTopNByOrderCountOrderByOrderCount_QueryDSL(Long limit) {
        return getSubscriptionJPAQuery()
                .orderBy(subscription.orderCount.desc())
                .limit(limit)
                .fetch();
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
    public List<Subscription> findTop3OrderByLikeCount() {
        List<Subscription> subscriptionList = getSubscriptionJPAQuery()
                .orderBy(subscription.subscriptLikeCount.desc())
                .limit(3L)
                .fetch();
        return subscriptionList;
    }

    @Override
    public List<Subscription> findTopNOrderByReviewCount_QueryDSL(Long limit) {
        List<Subscription> subscriptionList = getSubscriptionJPAQuery()
                .orderBy(subscription.reviewCount.asc())
                .limit(limit)
                .fetch();
        return subscriptionList;
    }

    @Override
    public Page<Subscription> findAllBySearchWithPaging_QueryDSL(Pageable pageable, SubscriptionSearchDTO searchDTO) {
        log.info(searchDTO.toString());
        //    해당 지역 주소를 포함하고 있는가? ex) 강남 in 서울시 '강남구' ...
        BooleanExpression hasAddress = searchDTO.getSearchFirstAddress() == null ? null : subscription.welfare.address.firstAddress.contains(searchDTO.getSearchFirstAddress());
        BooleanExpression hasSearchText = searchDTO.getSearchText() == null ? null : subscription.welfare.welfareName.contains(searchDTO.getSearchText());

        List<Subscription> subscriptionList = getSubscriptionJPAQuery()
                .from(subscription)
                .where(hasAddress, hasSearchText)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(subscription.count())
                .from(subscription)
                .where(hasAddress, hasSearchText).fetchOne();

        return new PageImpl<>(subscriptionList, pageable, count);
    }

    @Override
    public Optional<Subscription> findByIdWithDetail_QueryDSL(Long id) {
        Subscription sub = query.select(subscription)
                .from(subscription)
                .leftJoin(subscription.welfare).fetchJoin()
                .where(subscription.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(sub);
    }

    @Override
    public Integer existsByMemberIdAndSubscriptionId(Long memberId, Long subscriptionId) {
        Integer count = query.select(member.orderSubscriptions.size())
                .from(member)
                .where(member.id.eq(memberId), member.orderSubscriptions.any().subscription.id.eq(subscriptionId))
                .fetchOne();
        return count;
    }

    /* 중복 코드인 Subscription JPAQuery 리턴 */
    private JPAQuery<Subscription> getSubscriptionJPAQuery() {
        return query.select(subscription)
                .from(subscription)
                .leftJoin(subscription.foodCalendars).fetchJoin()
                .join(subscription.welfare).fetchJoin();
    }
}
