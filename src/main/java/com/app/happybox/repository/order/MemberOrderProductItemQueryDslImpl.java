package com.app.happybox.repository.order;

import com.app.happybox.entity.order.MemberOrderProductItem;
import com.app.happybox.entity.type.PurchaseStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.app.happybox.entity.order.QMemberOrderProductItem.memberOrderProductItem;

@RequiredArgsConstructor
public class MemberOrderProductItemQueryDslImpl implements MemberOrderProductItemQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Page<MemberOrderProductItem> findOrderListByMemberIdAndSearchDateDescWithPaging_QueryDSL(Pageable pageable, Long memberId, LocalDateTime searchStartDate, LocalDateTime searchEndDate) {
        List<MemberOrderProductItem> memberOrderProductItemList = query.select(memberOrderProductItem)
                .from(memberOrderProductItem)
                .join(memberOrderProductItem.memberOrderProduct).fetchJoin()
                .join(memberOrderProductItem.product).fetchJoin()
                .join(memberOrderProductItem.memberOrderProduct).fetchJoin()
                .where(memberOrderProductItem.memberOrderProduct.member.id.eq(memberId))
                .where(memberOrderProductItem.memberOrderProduct.purchaseStatus.eq(PurchaseStatus.CONFIRMED))
                .where(memberOrderProductItem.createdDate.between(searchStartDate, searchEndDate))
                .orderBy(memberOrderProductItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(memberOrderProductItem.id.count()).from(memberOrderProductItem).fetchOne();

        return new PageImpl<>(memberOrderProductItemList, pageable, count);
    }

    @Override
    public Page<MemberOrderProductItem> findCancleListByMemberIdAndSearchDateDescWithPaging_QueryDSL(Pageable pageable, Long memberId, LocalDateTime searchStartDate, LocalDateTime searchEndDate) {
        List<MemberOrderProductItem> memberOrderProductItemList = query.select(memberOrderProductItem)
                .from(memberOrderProductItem)
                .join(memberOrderProductItem.memberOrderProduct).fetchJoin()
                .join(memberOrderProductItem.product).fetchJoin()
                .join(memberOrderProductItem.memberOrderProduct).fetchJoin()
                .where(memberOrderProductItem.memberOrderProduct.member.id.eq(memberId))
                .where(memberOrderProductItem.memberOrderProduct.purchaseStatus.eq(PurchaseStatus.CANCELED))
                .where(memberOrderProductItem.createdDate.between(searchStartDate, searchEndDate))
                .orderBy(memberOrderProductItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(memberOrderProductItem.id.count()).from(memberOrderProductItem).fetchOne();

        return new PageImpl<>(memberOrderProductItemList, pageable, count);
    }

    @Override
    public Page<MemberOrderProductItem> findSaleListByDistributorIdAndSearchDateDescWithPaging_QueryDSL(Pageable pageable, Long distributorId, LocalDateTime searchStartDate, LocalDateTime searchEndDate) {
        List<MemberOrderProductItem> memberOrderProductItemList = query.select(memberOrderProductItem)
                .from(memberOrderProductItem)
                .join(memberOrderProductItem.product).fetchJoin()
                .join(memberOrderProductItem.memberOrderProduct).fetchJoin()
                .where(memberOrderProductItem.product.distributor.id.eq(distributorId))
                .where(memberOrderProductItem.memberOrderProduct.purchaseStatus.eq(PurchaseStatus.CONFIRMED))
                .where(memberOrderProductItem.createdDate.between(searchStartDate, searchEndDate))
                .orderBy(memberOrderProductItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(memberOrderProductItem.id.count())
                .from(memberOrderProductItem)
                .where(memberOrderProductItem.product.distributor.id.eq(distributorId))
                .fetchOne();

        return new PageImpl<>(memberOrderProductItemList, pageable, count);
    }

    @Override
    public Long findSaleCountByDistributorAndPurchaseStatus_QueryDSL(Long distributorId) {
        Long count = query.select(memberOrderProductItem.memberOrderProduct.member.id.count())
                .from(memberOrderProductItem)
                .where(memberOrderProductItem.product.distributor.id.eq(distributorId))
                .where(memberOrderProductItem.memberOrderProduct.purchaseStatus.eq(PurchaseStatus.CONFIRMED))
                .fetchOne();

        return count;
    }

    @Override
    public Page<MemberOrderProductItem> findCancleListByDistributorIdAndSearchDateDescWithPaging_QueryDSL(Pageable pageable, Long distributorId, LocalDateTime searchStartDate, LocalDateTime searchEndDate) {
        List<MemberOrderProductItem> memberOrderProductItemList = query.select(memberOrderProductItem)
                .from(memberOrderProductItem)
                .join(memberOrderProductItem.product).fetchJoin()
                .join(memberOrderProductItem.memberOrderProduct).fetchJoin()
                .where(memberOrderProductItem.product.distributor.id.eq(distributorId))
                .where(memberOrderProductItem.memberOrderProduct.purchaseStatus.eq(PurchaseStatus.CANCELED))
                .where(memberOrderProductItem.createdDate.between(searchStartDate, searchEndDate))
                .orderBy(memberOrderProductItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(memberOrderProductItem.id.count())
                .from(memberOrderProductItem)
                .where(memberOrderProductItem.product.distributor.id.eq(distributorId))
                .fetchOne();

        return new PageImpl<>(memberOrderProductItemList, pageable, count);
    }

    @Override
    public Long findCancleCountByDistributorAndPurchaseStatus_QueryDSL(Long distributorId) {
        Long count = query.select(memberOrderProductItem.memberOrderProduct.member.id.count())
                .from(memberOrderProductItem)
                .where(memberOrderProductItem.product.distributor.id.eq(distributorId))
                .where(memberOrderProductItem.memberOrderProduct.purchaseStatus.eq(PurchaseStatus.CANCELED))
                .fetchOne();

        return count;
    }
}
