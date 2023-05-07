package com.app.happybox.repository.order;

import com.app.happybox.entity.order.ProductDTO;
import com.app.happybox.entity.order.QProduct;
import com.app.happybox.entity.order.QProductDTO;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.app.happybox.entity.order.QProduct.product;

@RequiredArgsConstructor
public class ProductQueryDslImpl implements ProductQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<ProductDTO> findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL() {
        List<ProductDTO> productDTOList = getProductJPAQuery()
                .orderBy(product.createdDate.desc())
                .limit(8L)
                .fetch();
        return productDTOList;
    }

    @Override
    public ProductDTO findByIdWithDetail_QueryDSL(Long id) {
        ProductDTO productDTO = getProductJPAQuery()
                .where(product.id.eq(id))
                .fetchOne();

        return productDTO;
    }

    private JPAQuery<ProductDTO> getProductJPAQuery() {
        return query.select(new QProductDTO(
                product.id,
                product.productName,
                product.productPrice,
                product.productStock,
                product.distributor.distributorName,
                product.productReplies.size())
        )
                .from(product);
    }
}
