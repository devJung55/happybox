package com.app.happybox.service.order;

import com.app.happybox.domain.OrderSubscriptionDTO;
import com.app.happybox.entity.order.OrderSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderSubsciptionService {
//    관리자 복지관 구독자 목록
    public Page<OrderSubscriptionDTO> getListByWelfareId(Pageable pageable, Long welfare, String subscriberName);

    default OrderSubscriptionDTO adminToOrderSubscriptionDTO(OrderSubscription orderSubscription) {
        return OrderSubscriptionDTO.builder()
                .memberId(orderSubscription.getMember().getId())
                .memberName(orderSubscription.getMember().getMemberName())
                .orderStartDate(orderSubscription.getCreatedDate())
                .build();
    }
}
