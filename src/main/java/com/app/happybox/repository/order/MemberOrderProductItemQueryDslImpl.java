package com.app.happybox.repository.order;

import com.app.happybox.entity.order.MemberOrderProductItem;
import com.app.happybox.entity.order.QMemberOrderProduct;
import com.app.happybox.entity.type.PurchaseStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.happybox.entity.order.QMemberOrderProductItem.memberOrderProductItem;

@RequiredArgsConstructor
public class MemberOrderProductItemQueryDslImpl implements MemberOrderProductItemQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<MemberOrderProductItem> findOrderListByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        List<MemberOrderProductItem> memberOrderProductItemList = query.select(memberOrderProductItem)
                .from(memberOrderProductItem)
                .join(memberOrderProductItem.memberOrderProduct).fetchJoin()
                .join(memberOrderProductItem.product).fetchJoin()
                .join(memberOrderProductItem.memberOrderProduct).fetchJoin()
                .where(memberOrderProductItem.memberOrderProduct.member.id.eq(memberId))
                .where(memberOrderProductItem.memberOrderProduct.purchaseStatus.eq(PurchaseStatus.CONFIRMED))
                .orderBy(memberOrderProductItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(memberOrderProductItem.id.count()).from(memberOrderProductItem).fetchOne();

        return new PageImpl<>(memberOrderProductItemList, pageable, count);
    }

    @Override
    public Page<MemberOrderProductItem> findCancleListByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        List<MemberOrderProductItem> memberOrderProductItemList = query.select(memberOrderProductItem)
                .from(memberOrderProductItem)
                .join(memberOrderProductItem.memberOrderProduct).fetchJoin()
                .join(memberOrderProductItem.product).fetchJoin()
                .join(memberOrderProductItem.memberOrderProduct).fetchJoin()
                .where(memberOrderProductItem.memberOrderProduct.member.id.eq(memberId))
                .where(memberOrderProductItem.memberOrderProduct.purchaseStatus.eq(PurchaseStatus.CANCELED))
                .orderBy(memberOrderProductItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(memberOrderProductItem.id.count()).from(memberOrderProductItem).fetchOne();

        return new PageImpl<>(memberOrderProductItemList, pageable, count);
    }
}
