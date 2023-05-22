package com.app.happybox.repository.payment;

import com.app.happybox.entity.payment.Payment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.happybox.entity.payment.QPayment.payment;

@RequiredArgsConstructor
public class PaymentQueryDslImpl implements PaymentQueryDsl {
    private final JPAQueryFactory query;

//    관리자 결제 목록
    @Override
    public Page<Payment> findAllWithPaging_QueryDSL(Pageable pageable) {
        List<Payment> payments = query.select(payment)
                .from(payment)
                .join(payment.order).fetchJoin()
                .join(payment.user).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(payment.id.count()).from(payment).fetchOne();
        return new PageImpl<>(payments, pageable, count);
    }
}
