package com.app.happybox.entity.order;

import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.type.SubOption;
import com.app.happybox.type.SubscriptStatus;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
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

    // 구독 옵션 (저염식, 양많이, 양적게 등)
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'NORMAL'")
    private SubOption subOption;

    /* 구독 상태 */
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'SUBSCRIBED'")
    @NotNull
    private SubscriptStatus subscriptStatus;

    public OrderSubscription(String receiverName, String receiverPhoneNumber, Address orderAddress, Subscription subscription, Member member) {
        super(receiverName, receiverPhoneNumber, orderAddress);
        this.subscription = subscription;
        this.member = member;
    }

    public void setSubOption(SubOption subOption) {
        this.subOption = subOption;
    }

    public void setSubscriptStatus(SubscriptStatus subscriptStatus) {
        this.subscriptStatus = subscriptStatus;
    }
}
