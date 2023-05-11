package com.app.happybox.entity.payment;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.order.Order;
import com.app.happybox.type.PaymentStatus;
import com.app.happybox.entity.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity @Table(name = "TBL_PAYMENT")
@DynamicInsert
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    /* 결제 기본 정보 */
    @ColumnDefault(value = "0")
    private Integer paymentAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    /* ============ */

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Order order;
}
