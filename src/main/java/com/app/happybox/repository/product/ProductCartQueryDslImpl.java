package com.app.happybox.repository.product;

import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.product.ProductCart;
import com.app.happybox.entity.product.QProductCart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

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

//    cart id로 product조회
    @Override
    public Optional<Product> findProductById(Long id) {
        return Optional.ofNullable(query.select(productCart.product)
                .from(productCart)
                .where(productCart.id.eq(id))
                .fetchOne()
        );
    }
//      product ID로 file조회
}
