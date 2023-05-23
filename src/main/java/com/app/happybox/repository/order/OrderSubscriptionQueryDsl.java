package com.app.happybox.repository.order;

import com.app.happybox.entity.order.OrderSubscription;
import com.app.happybox.entity.user.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderSubscriptionQueryDsl {
//    마이페이지 구독조회
    public Optional<OrderSubscription> findSubscriptionByMemberId_QueryDSL(Long memberId);

//    마이페이지 구독건수 조회
    public Long findSubscriptionCountByMemberId_QueryDSL(Long memberId);

//    마이페이지 구독자 목록
    public Page<OrderSubscription> findSubscriberListByWelfareIdDescWithPaging_QueryDSL(Pageable pageable, Long welfareId, String subscriberName);

//    마이페이지 구독자 수 조회
    public Long findSubscriberCountByWelfareId_QueryDSL(Long welfareId);

//    관리자 페이지 복지관 구독 구독자목록
    public Page<Member> findAllMembersByWelfareId(Pageable pageable, Long welfareId, String subscriberName);
}
