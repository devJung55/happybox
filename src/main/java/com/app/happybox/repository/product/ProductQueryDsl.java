package com.app.happybox.repository.product;

import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.product.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductQueryDsl {
//    최신순 8개 조회
    public List<Product> findTop8WithDistributorAndReviewCountOrderByDate_QueryDSL();
//    상세 보기
    public Optional<Product> findByIdWithDetail_QueryDSL(Long id);
//    상품 동적검색
    public Page<Product> findAllByProductSearch_QueryDSL(Pageable pageable, ProductSearch productSearch);
//    마이페이지 상품 목록
    public Page<Product> findAllByDistributorIdWithPaging_QueryDSL(Pageable pageable, Long distributorId);
}
