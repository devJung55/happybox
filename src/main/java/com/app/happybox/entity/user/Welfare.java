package com.app.happybox.entity.user;

import com.app.happybox.entity.board.DonationBoard;
import com.app.happybox.entity.order.WelfareOrderProduct;
import com.app.happybox.entity.payment.Payment;
import com.app.happybox.entity.subscript.Subscription;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_WELFARE")
@DiscriminatorValue("WELFARE")
@DynamicInsert
@Getter @ToString(callSuper = true, exclude = {
        "payments", "welfareOrderProducts", "subscription", "donationBoards"
}) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Welfare extends User {

    /* 복지관 기본 정보 */
    @NotNull @Column(unique = true)
    private String welfareName;

    // 포인트 보유량
    @ColumnDefault(value = "0")
    private Integer welfarePointTotal;
    /* ============= */

    /* 회원 게시글 목록 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "welfare", orphanRemoval = true)
    private List<DonationBoard> donationBoards = new ArrayList<>();

    /* 회원 결제 내역 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();

    /* 회원 주문 목록 (일반회원, 복지관회원) */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "welfare", orphanRemoval = true)
    private List<WelfareOrderProduct> welfareOrderProducts = new ArrayList<>();

    /* 복지관의 구독 상품 기본적으로 복지관 당 하나 (OneToOne) */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "welfare", orphanRemoval = true)
    private Subscription subscription;
}
