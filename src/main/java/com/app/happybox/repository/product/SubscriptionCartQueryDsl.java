package com.app.happybox.repository.product;

import com.app.happybox.entity.product.SubscriptionCart;
import com.app.happybox.entity.subscript.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionCartQueryDsl {

    public List<SubscriptionCart> findAllByUserId_QueryDSL(Long id);

    public List<SubscriptionCart> findAllByIdsWithDetail_QueryDSL(List<Long> ids);

    public Optional<SubscriptionCart> existCartByMemberIdAndSubscriptionId(Long memberId, Long subscriptionId);

    public void deleteCart(Long id);
}
