package com.app.happybox.service.payment;

import com.app.happybox.domain.PaymentDTO;
import com.app.happybox.entity.payment.Payment;
import com.app.happybox.type.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
//    관리자 결제 목록
    public Page<PaymentDTO> getList(Pageable pageable);

//    관리자 결제 삭제
    public void removePayment(Long id);

    default PaymentDTO paymentToDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .paymentAmount(payment.getPaymentAmount())
                .paymentStatus(payment.getPaymentStatus() == PaymentStatus.COMPLETE ? "결제완료" : "결제취소")
                .createdDate(payment.getCreatedDate())
                .build();
    }
}
