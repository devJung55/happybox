package com.app.happybox.repository.payment;

import com.app.happybox.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> ,PaymentQueryDsl{
}
