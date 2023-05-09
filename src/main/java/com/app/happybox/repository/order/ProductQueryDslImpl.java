package com.app.happybox.repository.order;

import com.app.happybox.entity.order.Product;
import com.app.happybox.entity.order.ProductSearch;
import com.app.happybox.entity.type.ProductCategory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<Product> findAllByProductSearch
            (Pageable pageable, ProductSearch productSearch) {

//        주소 검색
        BooleanExpression productAddressEq =
                productSearch.getAddress() == null ?
                        null : product.distributor.address.firstAddress.contains(productSearch.getAddress());
//        가격 검색
        BooleanExpression productPriceGoe =
                productSearch.getPrice() == null ?
                        null : product.productPrice.goe(productSearch.getPrice());
//        이름 검색
        BooleanExpression productNameLike =
                productSearch.getName() == null ?
                        null : product.productName.like(productSearch.getName());
//        카테고리 검색
        BooleanExpression productCategoryEq =
                productSearch.getProductCategory() == null ?
                        null : product.productCategory.eq(productSearch.getProductCategory());

        List<Product> productList = query.select(product)
                .from(product)
                .join(product.distributor)
                .join(product.productFiles).fetchJoin()
                .where(productAddressEq, productPriceGoe, productNameLike, productCategoryEq)
                .fetch();

        Long total = query.select(product.count())
                .from(product)
                .where(productAddressEq, productPriceGoe, productNameLike, productCategoryEq)
                .fetchOne();

        return new PageImpl<>(productList, pageable, total);
    }


    private JPAQuery<Product> getProductJPAQuery() {
        return query.select(product)
                .from(product)
                .join(product.distributor).fetchJoin()
                .leftJoin(product.productFiles);
    }

//    동적 정렬 OrderSpecf
//    private OrderSpecifier[] createOrderSpecifier(OrderCondition orderCondition) {
//
//        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
//
//        if(Objects.isNull(orderCondition)){
//            orderSpecifiers.add(new OrderSpecifier(Order.DESC, person.name));
//        }else if(orderCondition.equals(OrderCondition.AGE)){
//            orderSpecifiers.add(new OrderSpecifier(Order.DESC, person.age));
//        }else{
//            orderSpecifiers.add(new OrderSpecifier(Order.DESC, person.region));
//        }
//        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
//    }
//}
