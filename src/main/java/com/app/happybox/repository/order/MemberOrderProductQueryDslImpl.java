package com.app.happybox.repository.order;

import com.app.happybox.entity.type.PurchaseStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.app.happybox.entity.order.QMemberOrderProduct.memberOrderProduct;

@RequiredArgsConstructor
public class MemberOrderProductQueryDslImpl implements MemberOrderProductQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Long findOrderCountByMemberIdAndOrderStatus_QueryDSL(Long memberId) {
        Long count = query.select(memberOrderProduct.member.id.count())
                .from(memberOrderProduct)
                .where(memberOrderProduct.member.id.eq(memberId))
                .where(memberOrderProduct.purchaseStatus.eq(PurchaseStatus.CONFIRMED))
                .fetchOne();

        return count;
    }
}
