package com.app.happybox.service.order;

import com.app.happybox.domain.OrderSubscriptionDTO;
import com.app.happybox.entity.order.OrderSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OrderSubsciptionService {
//    관리자 복지관 구독자 목록
    public Page<OrderSubscriptionDTO> getListByWelfareId(Pageable pageable, Long welfare, String subscriberName);

//    일반 마이페이지 구독한 수 조회
    public Long getMySubscriptionCountByMemberId(Long id);

//    마이페이지 구독 상세보기
    public OrderSubscriptionDTO getSubscriptionDetailByMemberId(Long memberId);

    default OrderSubscriptionDTO adminToOrderSubscriptionDTO(OrderSubscription orderSubscription) {
        return OrderSubscriptionDTO.builder()
                .memberId(orderSubscription.getMember().getId())
                .memberName(orderSubscription.getMember().getMemberName())
                .orderStartDate(orderSubscription.getCreatedDate())
                .build();
    }

    default OrderSubscriptionDTO mypageToOrderSubscriptionDTO(OrderSubscription orderSubscription) {
        return OrderSubscriptionDTO.builder()
                .id(orderSubscription.getSubscription().getId())
                .welfareId(orderSubscription.getSubscription().getWelfare().getId())
                .welfareName(orderSubscription.getSubscription().getWelfare().getWelfareName())
                .subscriptionTitle(orderSubscription.getSubscription().getSubscriptionTitle())
                .subscriptionContent(orderSubscription.getSubscription().getSubscriptionContent())
                .orderStartDate(orderSubscription.getCreatedDate())
                .build();
    }
}
