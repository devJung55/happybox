package com.app.happybox.entity.product;

import com.app.happybox.type.ProductCategory;
import com.app.happybox.type.ProductSearchOrder;
import lombok.Data;

@Data
public class ProductSearch {
    private String address;
    private Integer price;
    private String name;
    private ProductCategory productCategory;
    private ProductSearchOrder productSearchOrder;
}
