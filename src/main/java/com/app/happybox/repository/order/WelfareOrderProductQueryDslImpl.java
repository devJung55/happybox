package com.app.happybox.repository.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.app.happybox.entity.order.QWelfareOrderProduct.welfareOrderProduct;

@RequiredArgsConstructor
public class WelfareOrderProductQueryDslImpl implements WelfareOrderProductQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Long findOrderCountByWelfareId_QueryDSL(Long welfareId) {
        Long count = query.select(welfareOrderProduct.id.count())
                .from(welfareOrderProduct)
                .where(welfareOrderProduct.welfare.id.eq(welfareId))
                .fetchOne();

        return count;
    }
}
