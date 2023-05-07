package com.app.happybox.repository.order;

import com.app.happybox.entity.order.Product;
import com.app.happybox.entity.order.ProductDTO;

import java.util.List;

public interface ProductQueryDsl {
//    최신순 8개 조회
    public List<ProductDTO> findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL();
}
