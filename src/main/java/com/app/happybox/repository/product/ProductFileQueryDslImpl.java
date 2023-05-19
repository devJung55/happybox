package com.app.happybox.repository.product;

import com.app.happybox.entity.file.ProductFile;
import com.app.happybox.entity.file.QProductFile;
import com.app.happybox.type.FileRepresent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.app.happybox.entity.file.QProductFile.*;

@RequiredArgsConstructor
public class ProductFileQueryDslImpl implements ProductFileQueryDsl {

    private final JPAQueryFactory query;


//    상품 Id로 대표 file 조회하기
    @Override
    public Optional<ProductFile> findFileByProductId(Long id) {
        return Optional.ofNullable(
                query.select(productFile)
                .from(productFile)
                .where(productFile.product.id.eq(id).and(productFile.fileRepresent.eq(FileRepresent.REPRESENT)))
                .fetchOne()
        );
    }
}
