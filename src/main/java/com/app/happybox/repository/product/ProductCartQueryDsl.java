package com.app.happybox.repository.product;

import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.product.ProductCart;

import java.util.List;
import java.util.Optional;

public interface ProductCartQueryDsl {
//    회원 id로 삭제
    public Long deleteByUserId_QueryDSL(Long id);
//    회원 id로 List조회
    public List<ProductCart> findAllByUserId_QueryDSL(Long id);
//    id들로 상세조회
    public List<ProductCart> findAllByIdsWithDetail_QueryDSL(List<Long> ids);

//    회원id로 Product 조회
    public Optional<Product> findProductById(Long id);

//    회원 아이디로 ProductFile 조회
}
