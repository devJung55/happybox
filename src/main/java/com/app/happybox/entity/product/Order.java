package com.app.happybox.entity.product;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "TBL_ORDER")
@Getter @ToString(exclude = "user") @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    /* 주문 안의 상품 List */
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Embedded
    private Address address;

    /* 주문한 회원 */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
