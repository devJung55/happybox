package com.app.happybox.repository.product;

import com.app.happybox.entity.file.ProductFile;

import java.util.Optional;

public interface ProductFileQueryDsl {

//    상품Id로 대표파일 조회하기
    public Optional<ProductFile> findFileByProductId(Long id);

}
