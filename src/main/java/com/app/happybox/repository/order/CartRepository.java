package com.app.happybox.repository.order;

import com.app.happybox.entity.product.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository  extends JpaRepository<Cart, Long>, CartQueryDsl {
}
