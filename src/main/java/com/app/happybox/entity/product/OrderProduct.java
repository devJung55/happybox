package com.app.happybox.entity.product;

import com.app.happybox.audity.Period;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 주문 시 주문한 상품 내역 (주문 안에 여러 상품이 있기 때문)
 * */
@Entity @Table(name = "TBL_ORDER_PRODUCT")
public class OrderProduct extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    /* 주문한 상품 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;

    /* 주문 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Order order;
}
