package com.app.happybox.repository.order;

import com.app.happybox.entity.order.OrderSubscription;

import java.util.Optional;

public interface OrderSubscriptionQueryDsl {
//    마이페이지 구독조회
    public Optional<OrderSubscription> findSubscriptionByMemberId_QueryDSL(Long memberId);
}
