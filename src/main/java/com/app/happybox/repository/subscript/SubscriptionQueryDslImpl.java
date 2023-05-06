package com.app.happybox.repository.subscript;

import com.app.happybox.entity.board.QReviewBoard;
import com.app.happybox.entity.subscript.QSubscription;
import com.app.happybox.entity.subscript.QSubscriptionDTO;
import com.app.happybox.entity.subscript.SubscriptionDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.app.happybox.entity.subscript.QSubscription.subscription;

@RequiredArgsConstructor
public class SubscriptionQueryDslImpl implements SubscriptionQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<SubscriptionDTO> findTop3BetweenDateOrderByDateDesc_QueryDSL(LocalDateTime startDate, LocalDateTime endDate) {
        List<SubscriptionDTO> subscriptionDTOList = query.select(new QSubscriptionDTO(
                subscription.id,
                subscription.subscriptionTitle,
                subscription.subscriptionPrice,
                subscription.subscriptLikeCount,
                subscription.subOption,
                subscription.welfare.welfareName
        ))
                .from(subscription)
                .where(subscription.createdDate.between(startDate, endDate))
                .orderBy(subscription.createdDate.desc())
                .limit(3L)
                .fetch();

        return subscriptionDTOList;
    }

    @Override
    public List<SubscriptionDTO> findTop4ByReviewCountOrderByReviewCount_QueryDSL() {
        List<SubscriptionDTO> subscriptionDTOList = query.select(new QSubscriptionDTO(
                subscription.id,
                subscription.subscriptionTitle,
                subscription.subscriptionPrice,
                subscription.subscriptLikeCount,
                subscription.subOption,
                subscription.welfare.welfareName,
                subscription.reviewBoards.size().longValue()
        ))
                .from(subscription)
                .orderBy(subscription.reviewBoards.size().longValue().desc())
                .fetch();
        return subscriptionDTOList;
    }
}
