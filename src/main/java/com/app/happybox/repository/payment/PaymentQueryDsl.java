package com.app.happybox.repository.payment;

import com.app.happybox.entity.payment.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentQueryDsl {
//    관리자 결제 목록
    public Page<Payment> findAllWithPaging_QueryDSL(Pageable pageable);
}
