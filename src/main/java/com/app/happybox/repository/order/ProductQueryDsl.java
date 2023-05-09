package com.app.happybox.repository.order;

import com.app.happybox.entity.order.Product;
import com.app.happybox.entity.order.Product;

import java.util.List;
import java.util.Optional;

public interface ProductQueryDsl {
//    최신순 8개 조회
    public List<Product> findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL();
//    상세 보기
    public Optional<Product> findByIdWithDetail_QueryDSL(Long id);

}
