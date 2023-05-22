package com.app.happybox.service.payment;

import com.app.happybox.domain.PaymentDTO;
import com.app.happybox.entity.payment.Payment;
import com.app.happybox.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("payment")
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

//    관리자 결제 목록
    @Override
    public Page<PaymentDTO> getList(Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAllWithPaging_QueryDSL(pageable);
        List<PaymentDTO> paymentDTOS = payments.get().map(this::paymentToDTO).collect(Collectors.toList());
        return new PageImpl<>(paymentDTOS, pageable, payments.getTotalElements());
    }

//    관리자 결제 삭제
    @Override
    public void removePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
