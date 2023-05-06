package com.app.happybox.entity.file;

import com.app.happybox.entity.order.Product;
import com.app.happybox.entity.subscript.Food;
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
}
