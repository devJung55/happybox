package com.app.happybox.entity.product;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.app.happybox.type.SubOption;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity @Table(name = "TBL_SUBSCRIPTION_CART")
@DynamicInsert
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptionCart extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subscription subscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'NORMAL'")
    private SubOption subOption;

    @Builder
    public SubscriptionCart(Subscription subscription, Member member, SubOption subOption) {
        this.subscription = subscription;
        this.member = member;
        this.subOption = subOption;
    }
}
