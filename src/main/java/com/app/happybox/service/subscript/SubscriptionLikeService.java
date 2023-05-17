package com.app.happybox.service.subscript;

public interface SubscriptionLikeService {
    public boolean checkOutLike(Long subscriptionId, Long userId);

    public boolean checkLike(Long subscriptionId, Long memberId);
}
