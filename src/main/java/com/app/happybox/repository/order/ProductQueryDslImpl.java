package com.app.happybox.repository.order;

import com.app.happybox.entity.order.Product;
import com.app.happybox.entity.order.ProductSearch;
import com.app.happybox.entity.type.ProductCategory;
import com.app.happybox.entity.type.ProductSearchOrder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        Product result = getProductJPAQuery()
                .where(product.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<Product> findAllByProductSearch_QueryDSL
            (Pageable pageable, ProductSearch productSearch) {

//        주소 검색
        BooleanExpression productAddressContains =
                productSearch.getAddress() == null ?
                        null : product.distributor.address.firstAddress.contains(productSearch.getAddress());
//        가격 검색
        BooleanExpression productPriceGoe =
                productSearch.getPrice() == null ?
                        null : product.productPrice.goe(productSearch.getPrice());
//        이름 검색
        BooleanExpression productNameContains =
                productSearch.getName() == null ?
                        null : product.productName.contains(productSearch.getName());
//        카테고리 검색
        BooleanExpression productCategoryEq =
                productSearch.getProductCategory() == null ?
                        null : product.productCategory.eq(productSearch.getProductCategory());

//        Product 쿼리
        List<Product> productList = query.select(product)
                .from(product)
                .leftJoin(product.distributor)
                .leftJoin(product.productFiles).fetchJoin()
                .where(productAddressContains, productPriceGoe, productNameContains, productCategoryEq)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(createOrderSpecifier(productSearch))
                .fetch();

//        total 쿼리
        Long total = query.select(product.count())
                .from(product)
                .where(productAddressContains, productPriceGoe, productNameContains, productCategoryEq)
                .fetchOne();

        return new PageImpl<>(productList, pageable, total);
    }

    @Override
    public Page<Product> findAllByDistributorIdWithPaging_QueryDSL(Pageable pageable, Long distributorId) {
        List<Product> productList = query.select(product)
                .from(product)
                .join(product.distributor).fetchJoin()
                .where(product.distributor.id.eq(distributorId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(product.id.count()).from(product).where(product.distributor.id.eq(distributorId)).fetchOne();

        return new PageImpl<>(productList, pageable, count);
    }

    private JPAQuery<Product> getProductJPAQuery() {
        return query.select(product)
                .from(product)
                .leftJoin(product.distributor)
                .leftJoin(product.productFiles).fetchJoin();
    }

    //    정렬 동적쿼리
    private OrderSpecifier createOrderSpecifier(ProductSearch productSearch) {
        OrderSpecifier orderSpecifier = null;
        ProductSearchOrder searchOrder = productSearch.getProductSearchOrder();

        if(searchOrder == null) {
            return new OrderSpecifier(Order.DESC, product.createdDate);
        }

        switch (searchOrder) {
            case DATE_DESC:
                orderSpecifier = new OrderSpecifier(Order.DESC, product.createdDate);
                break;
            case ORDER_COUNT_DESC:
                orderSpecifier = new OrderSpecifier(Order.DESC, product.productOrderCount);
                break;
            case PRICE_ASC:
                orderSpecifier = new OrderSpecifier(Order.ASC, product.productPrice);
                break;
            case PRICE_DESC:
                orderSpecifier = new OrderSpecifier(Order.DESC, product.productPrice);
                break;
            default:
                orderSpecifier = new OrderSpecifier(Order.DESC, product.createdDate);
        }

        return orderSpecifier;
    }

}
