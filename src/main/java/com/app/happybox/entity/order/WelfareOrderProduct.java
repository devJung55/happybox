package com.app.happybox.entity.order;

import com.app.happybox.entity.type.PurchaseStatus;
import com.app.happybox.entity.user.Welfare;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_WELFARE_ORDER_PRODUCT")
@DynamicInsert
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WelfareOrderProduct extends Order {

    /* 주문 안의 상품 List */
    @OneToMany(mappedBy = "welfareOrderProduct", fetch = FetchType.LAZY)
    private List<WelfareOrderProductItem> welfareOrderProductItems = new ArrayList<>();

    /* 주문/구독한 회원 */
    @ManyToOne(fetch = FetchType.LAZY)
    private Welfare welfare;

    /* 구매 상태 */
    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;
}
