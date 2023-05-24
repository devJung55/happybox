package com.app.happybox.repository.product;

import com.app.happybox.entity.product.Product;
import com.app.happybox.domain.product.ProductSearchDTO;
import com.app.happybox.type.ProductSearchOrder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.happybox.entity.product.QProduct.product;


@RequiredArgsConstructor
@Slf4j
public class ProductQueryDslImpl implements ProductQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<Product> findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL() {
        List<Product> ProductList = getProductJPAQuery()
                .orderBy(product.id.desc())
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
            (Pageable pageable, ProductSearchDTO productSearchDTO) {

//        주소 검색
        BooleanExpression productAddressContains =
                productSearchDTO.getAddress() == null ?
                        null : product.distributor.address.firstAddress.contains(productSearchDTO.getAddress());
//        가격 검색
        BooleanExpression productPriceGoe =
                productSearchDTO.getPrice() == null ?
                        null : product.productPrice.loe(productSearchDTO.getPrice());
//        이름 검색
        BooleanExpression productNameContains =
                productSearchDTO.getName() == null ?
                        null : product.productName.contains(productSearchDTO.getName());
//        카테고리 검색
        BooleanExpression productCategoryEq =
                productSearchDTO.getProductCategory() == null ?
                        null : product.productCategory.eq(productSearchDTO.getProductCategory());

//        Product 쿼리
        List<Product> productList = query.select(product)
                .from(product)
                .leftJoin(product.distributor).fetchJoin()
                .leftJoin(product.productFiles).fetchJoin()
                .where(productAddressContains, productPriceGoe, productNameContains, productCategoryEq)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(createOrderSpecifier(productSearchDTO))
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
                .leftJoin(product.productFiles).fetchJoin()
                .join(product.distributor).fetchJoin()
                .where(product.distributor.id.eq(distributorId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(product.id.count()).from(product).where(product.distributor.id.eq(distributorId)).fetchOne();

        return new PageImpl<>(productList, pageable, count);
    }

    @Override
    public Long findCountByDistributor_QueryDSL(Long distributorId) {
        Long productCount = query.select(product.id.count())
                .from(product)
                .where(product.distributor.id.eq(distributorId))
                .fetchOne();
        return productCount;
    }

    @Override
    public List<Product> findTop8WithDetailOrderByReplyCount_QueryDSL() {
        return getProductJPAQuery()
                .limit(8L)
                .orderBy(product.productReplyCount.desc())
                .fetch();
    }

    @Override
    public List<Product> findRandomProducts_QueryDSL() {

        Long totalCount = query.select(product.count()).from(product).fetchOne();
        int idx = (int) (Math.random() * totalCount) / 2;

        // page request
        PageRequest pageRequest = PageRequest.of(idx, 2);

        return getProductJPAQuery()
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
    }


    private JPAQuery<Product> getProductJPAQuery() {
        return query.select(product)
                .from(product)
                .join(product.distributor).fetchJoin()
                .leftJoin(product.productFiles).fetchJoin();
    }

    //    정렬 동적쿼리
    private OrderSpecifier createOrderSpecifier(ProductSearchDTO productSearchDTO) {
        OrderSpecifier orderSpecifier = null;
        ProductSearchOrder searchOrder = productSearchDTO.getProductSearchOrder();

        if (searchOrder == null) {
            return new OrderSpecifier(Order.DESC, product.id);
        }

        switch (searchOrder) {
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
