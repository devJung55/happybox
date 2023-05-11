package com.app.happybox.repository.product;

import com.app.happybox.entity.product.SubscriptionCart;

import java.util.List;

public interface SubscriptionCartQueryDsl {

    public List<SubscriptionCart> findAllByUserId_QueryDSL(Long id);

    public List<SubscriptionCart> findAllByIdsWithDetail_QueryDSL(List<Long> ids);
}
