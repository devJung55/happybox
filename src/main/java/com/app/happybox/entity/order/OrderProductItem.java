package com.app.happybox.entity.order;

import com.app.happybox.audity.Period;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

/**
 * 주문 시 주문한 상품 내역 (주문 안에 여러 상품이 있기 때문)
 * */
@Entity @Table(name = "TBL_ORDER_PRODUCT_ITEM")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProductItem extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private Long orderAmount;

    /* 주문한 상품 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;

    /* 주문 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private OrderProduct orderProduct;
}
