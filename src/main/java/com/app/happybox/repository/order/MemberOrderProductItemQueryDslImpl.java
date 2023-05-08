package com.app.happybox.repository.order;

import com.app.happybox.entity.order.MemberOrderProductItem;
import com.app.happybox.entity.user.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.app.happybox.entity.order.QMemberOrderProduct.memberOrderProduct;
import static com.app.happybox.entity.order.QMemberOrderProductItem.memberOrderProductItem;
import static com.app.happybox.entity.order.QProduct.product;
import static com.app.happybox.entity.user.QMember.member;

@RequiredArgsConstructor
public class MemberOrderProductItemQueryDslImpl implements MemberOrderProductItemQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<MemberOrderProductItem> findOrderListByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        List<MemberOrderProductItem> memberOrderProductItemList = query.select(memberOrderProductItem)
                .from(memberOrderProductItem)
                .join(memberOrderProduct)
                .join(product)
                .join(member)
                .fetchJoin()
                .where(memberOrderProductItem.memberOrderProduct.member.id.eq(memberId))
                .fetch();

        Long count = query.select(memberOrderProductItem.orderAmount.count()).from(memberOrderProductItem).fetchOne();

        return new PageImpl<>(memberOrderProductItemList, pageable, count);
    }
}
