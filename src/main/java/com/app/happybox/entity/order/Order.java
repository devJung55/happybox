package com.app.happybox.entity.order;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity @Table(name = "TBL_ORDER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ORDER_TYPE")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Order extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    /* 주문/구독한 회원 */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
