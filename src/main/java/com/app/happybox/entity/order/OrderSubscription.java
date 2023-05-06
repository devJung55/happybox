package com.app.happybox.entity.order;

import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.type.SubscriptStatus;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity @Table(name = "TBL_ORDER_SUBSCRIPTION")
@DynamicInsert
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSubscription extends Order {

    /* 구독상품 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Subscription subscription;

    /* 주문/구독한 회원 */
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    /* 구독 상태 */
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'SUBSCRIBED'")
    @NotNull
    private SubscriptStatus subscriptStatus;
}
