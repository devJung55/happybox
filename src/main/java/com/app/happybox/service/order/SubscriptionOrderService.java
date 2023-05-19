package com.app.happybox.service.order;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.domain.OrderInfoDTO;

import java.util.List;

public interface SubscriptionOrderService {
    // 주문과 결제내역 저장 후 결제금액 반환
    public Integer saveSubscriptionOrder(OrderInfoDTO orderInfoDTO);
}
