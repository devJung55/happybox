package com.app.happybox.service.product;

import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.product.ProductCart;
import com.app.happybox.entity.product.ProductCartDTO;
import com.app.happybox.entity.user.User;

import java.util.List;

public interface ProductCartService {

    public List<ProductCartDTO> findAllByUserId(Long id);

    public Long saveCart(ProductCartDTO cartDTO, Long userId, Long productId);

    default ProductCartDTO cartToDTO(ProductCart productCart) {
        return ProductCartDTO.builder()
                .id(productCart.getId())
                .cartOrderAmount(productCart.getCartOrderAmount())
                .productName(productCart.getProduct().getProductName())
                .build();
    }

    default ProductCart productCartDTOToEntity(ProductCartDTO productCartDTO, User user, Product product) {
        return ProductCart.builder()
                .user(user)
                .cartOrderAmount(productCartDTO.getCartOrderAmount())
                .product(product)
                .build();
    }
}
