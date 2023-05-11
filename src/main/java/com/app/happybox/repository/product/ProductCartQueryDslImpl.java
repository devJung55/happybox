package com.app.happybox.repository.product;

import com.app.happybox.entity.product.ProductCart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.app.happybox.entity.product.QProductCart.productCart;


@RequiredArgsConstructor
public class ProductCartQueryDslImpl implements ProductCartQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public Long deleteByUserId_QueryDSL(Long id) {
        long count = query.delete(productCart)
                .where(productCart.user.id.eq(id))
                .execute();
        return count;
    }

    @Override
    public List<ProductCart> findAllByUserId_QueryDSL(Long id) {
        return query.select(productCart)
                .from(productCart)
                .join(productCart.product).fetchJoin()
                .where(productCart.user.id.eq(id))
                .fetch();
    }

    @Override
    public List<ProductCart> findAllByIdsWithDetail_QueryDSL(List<Long> ids) {
        return query.select(productCart)
                .from(productCart)
                .join(productCart.product).fetchJoin()
                .where(productCart.id.in(ids))
                .fetch();
    }
}
