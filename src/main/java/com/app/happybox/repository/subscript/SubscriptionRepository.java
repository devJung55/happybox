package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, SubscriptionQueryDsl {

//    구독했는지 확인하기 위해 조회
    public boolean existsByWelfareId(Long welfareId);

//    subscription id로 조회
    public boolean existsById(Long id);

//    구독 정보 가져오기
    public Optional<Subscription> findById(Long id);

    public Optional<Subscription> findByWelfareId(Long id);

}
