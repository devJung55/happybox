package com.app.happybox.entity.order;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity @Table(name = "TBL_ORDER")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Order extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address orderAddress;

    public Order(Address orderAddress) {
        this.orderAddress = orderAddress;
    }
}
