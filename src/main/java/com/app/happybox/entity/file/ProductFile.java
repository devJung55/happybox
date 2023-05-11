package com.app.happybox.entity.file;

import com.app.happybox.entity.product.Product;
import com.app.happybox.type.FileRepresent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name = "TBL_PRODUCT_FILE")
@Getter @ToString(exclude = "product",callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductFile extends Files {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;

    //    파일이 대표 파일인지 여부
    @Enumerated(EnumType.STRING)
    private FileRepresent fileRepresent;
}
