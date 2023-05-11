package com.app.happybox.entity.order;

import com.app.happybox.entity.user.Address;
import com.app.happybox.type.PurchaseStatus;
import com.app.happybox.entity.user.Welfare;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
    @ColumnDefault(value = "'CONFIRMED'")
    private PurchaseStatus purchaseStatus;

    public WelfareOrderProduct(String receiverName, String receiverPhoneNumber, Address orderAddress, Welfare welfare) {
        super(receiverName, receiverPhoneNumber, orderAddress);
        this.welfare = welfare;
    }

    //    편의 메소드
    public void addProducts(List<WelfareOrderProductItem> items) {
        for (WelfareOrderProductItem item : items) {
            item.setWelfareOrderProduct(this);
            this.welfareOrderProductItems.add(item);
        }
    }
}
