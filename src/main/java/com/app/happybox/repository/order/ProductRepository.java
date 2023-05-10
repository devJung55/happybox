package com.app.happybox.repository.order;

import com.app.happybox.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryDsl {
}
