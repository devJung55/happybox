package com.app.happybox.repository.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.app.happybox.entity.product.QCart.cart;


@RequiredArgsConstructor
public class CartQueryDslImpl implements CartQueryDsl {
    private final JPAQueryFactory query;
    @Override
    public Long deleteByUserId_QueryDSL(Long id) {
        long count = query.delete(cart)
                .where(cart.user.id.eq(id))
                .execute();
        return count;
    }
}
