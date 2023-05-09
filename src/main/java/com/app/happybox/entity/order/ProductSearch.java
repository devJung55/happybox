package com.app.happybox.entity.order;

import com.app.happybox.entity.type.ProductCategory;
import com.app.happybox.entity.type.ProductSearchOrder;
import lombok.Data;

@Data
public class ProductSearch {
    private String address;
    private Integer price;
    private String name;
    private ProductCategory productCategory;
    private ProductSearchOrder productSearchOrder;
}
