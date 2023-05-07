package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.SubscriptionLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionLikeRepository extends JpaRepository<SubscriptionLike, Long>, SubscriptionLikeQueryDsl {
}
