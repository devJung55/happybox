package com.app.happybox.entity.order;

import com.app.happybox.type.PurchaseStatus;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_MEMBER_ORDER_PRODUCT")
@DynamicInsert
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberOrderProduct extends Order {

    /* 주문 안의 상품 List */
    @OneToMany(mappedBy = "memberOrderProduct", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<MemberOrderProductItem> welfareOrderProductItems = new ArrayList<>();

    /* 주문/구독한 회원 */
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    /* 구매 상태 */
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'CONFIRMED'")
    private PurchaseStatus purchaseStatus;

    public MemberOrderProduct(Address orderAddress, Member member) {
        super(orderAddress);
        this.member = member;
    }

    //    편의 메소드
    public void addProducts(List<MemberOrderProductItem> items) {
        for (MemberOrderProductItem item : items) {
            item.setMemberOrderProduct(this);
            this.welfareOrderProductItems.add(item);
        }
    }
}
