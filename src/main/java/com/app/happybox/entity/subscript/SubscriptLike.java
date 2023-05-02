package com.app.happybox.entity.subscript;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.Member;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_SUBSCRIPT_LIKE")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptLike extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    /* 구독중인 일반회원 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    /* 구독 대상 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Subscription subscription;
}
