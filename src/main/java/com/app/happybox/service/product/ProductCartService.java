package com.app.happybox.service.product;

import com.app.happybox.entity.product.ProductCart;
import com.app.happybox.entity.product.ProductCartDTO;

import java.util.List;

public interface ProductCartService {

    public List<ProductCartDTO> findAllByUserId(Long id);

    default ProductCartDTO cartToDTO(ProductCart productCart) {
        return ProductCartDTO.builder()
                .id(productCart.getId())
                .cartOrderAmount(productCart.getCartOrderAmount())
                .productName(productCart.getProduct().getProductName())
                .build();
    }
}
