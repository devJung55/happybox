package com.app.happybox.entity.order;

import com.app.happybox.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_ORDER")
@DynamicInsert
@DiscriminatorValue("PURCHASE")
@Getter @ToString(exclude = "user", callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase extends Order {

    /* 주문 안의 상품 List */
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<PurchaseProduct> purchaseProducts = new ArrayList<>();

    /* 구매 상태 */
    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;
}
