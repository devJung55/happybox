package com.app.happybox.entity.user;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.order.Purchase;
import com.app.happybox.entity.payment.Payment;
import com.app.happybox.entity.subscript.SubscriptionLike;
import com.app.happybox.entity.order.MemberSubscription;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_MEMBER")
@DiscriminatorValue("MEMBER")
@Getter @ToString(callSuper = true, exclude = "subscriptLikes") @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends User {

    /* 일반회원만 있는 정보 */
    @NotNull
    private String memberName;
    @NotNull
    private String memberMobile;
    @NotNull
    private LocalDate memberBirth;
    @NotNull @Enumerated(EnumType.STRING)
    private Gender memberGender;
    // 배송지 주소 정보
    @NotNull
    private Address memberDeliveryAddress;

    /* 회원 구독 좋아요 목록 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<SubscriptionLike> subscriptionLikes = new ArrayList<>();

    /* 회원 게시글 목록 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    /* 회원 구독 목록 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<MemberSubscription> memberSubscriptions = new ArrayList<>();

    /* 회원 주문 목록 (일반회원, 복지관회원) */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

    /* 회원 결제내역 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();
}
