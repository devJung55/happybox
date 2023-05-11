package com.app.happybox.repository.product;

import com.app.happybox.entity.product.ProductCart;

import java.util.List;

public interface ProductCartQueryDsl {
//    회원 id로 삭제
    public Long deleteByUserId_QueryDSL(Long id);
//    회원 id로 List조회
    public List<ProductCart> findAllByUserId_QueryDSL(Long id);
//    id로 상세조회
    public List<ProductCart> findAllByIdsWithDetail_QueryDSL(List<Long> ids);
}
