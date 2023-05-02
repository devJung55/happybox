package com.app.happybox.entity.user;

import com.app.happybox.entity.order.Purchase;
import com.app.happybox.entity.payment.Payment;
import com.app.happybox.entity.subscript.Subscription;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_WELFARE")
@DiscriminatorValue("WELFARE")
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Welfare extends User {

    /* 회원 결제내역 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();

    /* 회원 주문 목록 (일반회원, 복지관회원) */
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

    /* 복지관의 구독 상품 기본적으로 복지관 당 하나 (OneToOne) */
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "welfare", orphanRemoval = true)
    private Subscription subscription;
}
