package com.app.happybox.entity.welfare;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.Member;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity @Table(name = "TBL_SUBSCRIPT_USER")
@DynamicInsert
@Getter @ToString(exclude = {"member", "subscript"}) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptUser extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Subscript subscript;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'SUBSCRIBED'")
    @NotNull
    private SubscriptStatus subscriptStatus;

    public SubscriptUser(Subscript subscript, Member member) {
        this.subscript = subscript;
        this.member = member;
    }
}
