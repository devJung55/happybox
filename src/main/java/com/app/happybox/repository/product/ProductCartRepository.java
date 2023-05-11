package com.app.happybox.repository.product;

import com.app.happybox.entity.product.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCartRepository extends JpaRepository<ProductCart, Long>, ProductCartQueryDsl {
}
