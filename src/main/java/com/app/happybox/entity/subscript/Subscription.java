package com.app.happybox.entity.subscript;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.order.MemberSubscription;
import com.app.happybox.entity.user.Welfare;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_SUBSCRIPTION")
@Getter @ToString(exclude = {"welfare", "subscriptLikes"}) @NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate @DynamicInsert
public class Subscription extends Period {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    /* ===== 구독 상품 정보 ===== */
    @NotNull
    private String subscriptionName;

    @ColumnDefault(value = "0")
    private Integer subscriptionPrice;

    // 구독 좋아요 갯수 (조회가 많기 때문에 컬럼으로 추가했음)
    @ColumnDefault(value = "0")
    private Integer subscriptLikeCount;
    /* ======================= */

    /* 복지관 */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Welfare welfare;

    /* 음식 일정 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subscription")
    private List<FoodCalendar> foodCalendars = new ArrayList<>();

    /* 구독회원 정보 List (구독 상품과 회원은 N:N 관계여서 구독회원 정보 라는 중간테이블 존재) */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subscription")
    private List<MemberSubscription> memberSubscriptions = new ArrayList<>();

    /* 구독 좋아요 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subscription")
    private List<SubscriptionLike> subscriptionLikes = new ArrayList<>();
}
