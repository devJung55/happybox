package com.app.happybox.entity.order;

import com.app.happybox.entity.type.PurchaseStatus;
import com.app.happybox.entity.user.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_MEMBER_ORDER_PRODUCT")
@DynamicInsert
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberOrderProduct extends Order {

    /* 주문 안의 상품 List */
    @OneToMany(mappedBy = "memberOrderProduct", fetch = FetchType.LAZY)
    private List<MemberOrderProductItem> welfareOrderProductItems = new ArrayList<>();

    /* 주문/구독한 회원 */
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    /* 구매 상태 */
    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;
}
