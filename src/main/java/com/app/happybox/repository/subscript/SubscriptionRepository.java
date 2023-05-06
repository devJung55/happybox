package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, SubscriptionQueryDsl {
}
