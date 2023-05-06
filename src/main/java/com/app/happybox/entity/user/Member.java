package com.app.happybox.entity.user;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.order.MemberOrderProduct;
import com.app.happybox.entity.order.WelfareOrderProduct;
import com.app.happybox.entity.payment.Payment;
import com.app.happybox.entity.subscript.SubscriptionLike;
import com.app.happybox.entity.order.OrderSubscription;
import com.app.happybox.entity.type.Gender;
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
@Getter @ToString(callSuper = true, exclude = {
        "subscriptionLikes", "recipeBoards", "reviewBoards", "orderSubscriptions", "memberOrderProducts", "payments"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @NotNull @Embedded
    private Address memberDeliveryAddress;

    /* 회원 구독 좋아요 목록 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<SubscriptionLike> subscriptionLikes = new ArrayList<>();

    /* 회원 레시피 게시글 목록 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    private List<RecipeBoard> recipeBoards = new ArrayList<>();

    /* 회원 복지관 리뷰 게시글 목록 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    private List<ReviewBoard> reviewBoards = new ArrayList<>();

    /* 회원 구독 목록 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    private List<OrderSubscription> orderSubscriptions = new ArrayList<>();

    /* 회원 주문 목록 (일반 회원) */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    private List<MemberOrderProduct> memberOrderProducts = new ArrayList<>();

    /* 회원 결제내역 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();
}
