package com.app.happybox.entity.order;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_ORDER_PRODUCT")
@DynamicInsert
@DiscriminatorValue("PURCHASE")
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends Order {

    /* 주문 안의 상품 List */
    @OneToMany(mappedBy = "orderProduct", fetch = FetchType.LAZY)
    private List<OrderProductItem> orderProductItems = new ArrayList<>();

    /* 구매 상태 */
    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;
}
