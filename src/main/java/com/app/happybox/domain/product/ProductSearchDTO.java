package com.app.happybox.domain.product;

import com.app.happybox.type.ProductCategory;
import com.app.happybox.type.ProductSearchOrder;
import lombok.Data;

@Data
public class ProductSearchDTO {
    private String address;
    private Integer price;
    private String name;
    private ProductCategory productCategory;
    private ProductSearchOrder productSearchOrder;
}
