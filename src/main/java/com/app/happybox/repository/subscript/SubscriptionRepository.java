package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, SubscriptionQueryDsl {

//    구독했는지 확인하기 위해 조회
    public boolean existsByWelfareId(Long welfareId);
}
