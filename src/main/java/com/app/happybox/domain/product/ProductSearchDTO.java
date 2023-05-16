package com.app.happybox.domain.product;

import com.app.happybox.type.ProductCategory;
import com.app.happybox.type.ProductSearchOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ProductSearchDTO {
    private String address;
    private Integer price;
    private String name;
    private ProductCategory productCategory;
    private ProductSearchOrder productSearchOrder;
}
