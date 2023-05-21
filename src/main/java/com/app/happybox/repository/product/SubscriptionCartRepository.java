package com.app.happybox.repository.product;

import com.app.happybox.entity.product.SubscriptionCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionCartRepository extends JpaRepository<SubscriptionCart, Long>, SubscriptionCartQueryDsl {


}
