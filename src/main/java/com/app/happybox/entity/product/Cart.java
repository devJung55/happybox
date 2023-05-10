package com.app.happybox.entity.product;

import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity @Table(name = "TBL_CART")
@DynamicInsert
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @NotNull
    @ColumnDefault(value = "1")
    private Long cartOrderAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subscription subscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public Cart(Long cartOrderAmount, Product product, User user) {
        this.cartOrderAmount = cartOrderAmount;
        this.product = product;
        this.user = user;
    }

    public Cart(Subscription subscription, User user) {
        this.subscription = subscription;
        this.user = user;
    }
}
