package com.app.happybox.service.order;

import java.util.List;

public interface OrderProductService {

    // 주문과 결제내역 저장 후 결제금액 반환
    public Long saveProductOrder(List<Long> productCartIds, Long userId);
}
