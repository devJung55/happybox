package com.app.happybox.repository.order;

import com.app.happybox.entity.order.OrderSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSubscriptionRepository extends JpaRepository<OrderSubscription, Long>, OrderSubscriptionQueryDsl {
}
