package com.app.happybox.repository.order;

import com.app.happybox.entity.order.Product;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.order.QProduct.product;

@RequiredArgsConstructor
public class ProductQueryDslImpl implements ProductQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<Product> findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL() {
        List<Product> ProductList = getProductJPAQuery()
                .orderBy(product.createdDate.desc())
                .limit(8L)
                .fetch();
        return ProductList;
    }

    @Override
    public Optional<Product> findByIdWithDetail_QueryDSL(Long id) {
        Product result = getProductWithRepliesJPAQuery()
                .where(product.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    private JPAQuery<Product> getProductJPAQuery() {
        return query.select(product)
                .from(product)
                .join(product.distributor).fetchJoin()
                .leftJoin(product.productFiles);
    }

    private JPAQuery<Product> getProductWithRepliesJPAQuery() {
        return query.select(product)
                .from(product)
                .join(product.distributor).fetchJoin()
                .leftJoin(product.productFiles)
                .join(product.productReplies).fetchJoin();
    }
}
