package com.app.happybox.repository.product;

import com.app.happybox.entity.product.SubscriptionCart;
import com.app.happybox.entity.subscript.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionCartRepository extends JpaRepository<SubscriptionCart, Long>, SubscriptionCartQueryDsl {


}
