package com.app.happybox.repository.subscript;

import com.app.happybox.entity.board.QReviewBoard;
import com.app.happybox.entity.subscript.QSubscriptionDTO;
import com.app.happybox.entity.subscript.SubscriptionDTO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.app.happybox.entity.board.QReviewBoard.reviewBoard;
import static com.app.happybox.entity.subscript.QSubscription.subscription;

@RequiredArgsConstructor
public class SubscriptionQueryDslImpl implements SubscriptionQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<SubscriptionDTO> findTop8OrderByDate_QueryDSL() {
        List<SubscriptionDTO> subscriptionDTOList = getSubscriptionJPAQuery()
                .from(subscription)
                .orderBy(subscription.createdDate.desc())
                .limit(8L)
                .fetch();

        return subscriptionDTOList;
    }

    @Override
    public List<SubscriptionDTO> findTop3BetweenDateOrderByDateDesc_QueryDSL(LocalDateTime startDate, LocalDateTime endDate) {
        List<SubscriptionDTO> subscriptionDTOList = getSubscriptionJPAQuery()
                .from(subscription)
                .where(subscription.createdDate.between(startDate, endDate))
                .orderBy(subscription.createdDate.desc())
                .limit(3L)
                .fetch();

        return subscriptionDTOList;
    }

    @Override
    public List<SubscriptionDTO> findTop4OrderByReviewCount_QueryDSL() {
        List<SubscriptionDTO> subscriptionDTOList = getSubscriptionJPAQuery()
                .from(subscription)
                .orderBy(subscription.reviewBoards.size().longValue().asc())
                .limit(4L)
                .fetch();
        return subscriptionDTOList;
    }

    @Override
    public Page<SubscriptionDTO> findAllByAddressCategoryWithPaging_QueryDSL(Pageable pageable, String address) {
        //    추후 동적쿼리로 변경
        //    해당 지역 주소를 포함하고 있는가? ex) 강남 in 서울시 '강남구' ...
        BooleanExpression hasAddress = subscription.welfare.address.firstAddress.contains(address);

        List<SubscriptionDTO> subscriptionDTOList = getSubscriptionJPAQuery()
                .from(subscription)
                .where(hasAddress)
                .orderBy(subscription.reviewBoards.size().asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(subscription.count())
                .from(subscription)
                .where(hasAddress).fetchOne();

        return new PageImpl<>(subscriptionDTOList, pageable, count);
    }

    @Override
    public SubscriptionDTO findByIdWithDetail_QueryDSL(Long id) {
        SubscriptionDTO subscriptionDTO = query.select(new QSubscriptionDTO(
                subscription.id,
                subscription.subscriptionTitle,
                subscription.subscriptionPrice,
                subscription.subscriptLikeCount,
                subscription.welfare.address,
                subscription.subOption,
                subscription.welfare.welfareName,
                subscription.reviewBoards.size().longValue(),
                JPAExpressions.select(reviewBoard.reviewRating.avg().nullif(0.0)).from(reviewBoard).where(reviewBoard.subscription.id.eq(id)),
                subscription.orderSubscriptions.size().longValue()
        ))
                .from(subscription)
                .where(subscription.id.eq(id))
                .fetchOne();

        return subscriptionDTO;
    }

    /* 중복 코드인 SubscriptionDTO JPAQuery 리턴 */
    private JPAQuery<SubscriptionDTO> getSubscriptionJPAQuery() {
        return query.select(new QSubscriptionDTO(
                subscription.id,
                subscription.subscriptionTitle,
                subscription.subscriptionPrice,
                subscription.subscriptLikeCount,
                subscription.welfare.address,
                subscription.subOption,
                subscription.welfare.welfareName,
                subscription.reviewBoards.size().longValue()
        ));
    }
}
