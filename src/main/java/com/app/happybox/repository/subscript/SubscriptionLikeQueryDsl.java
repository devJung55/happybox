package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;

public interface SubscriptionLikeQueryDsl {
    public boolean checkMemberLikesSubscription_QueryDSL(Member member, Subscription subscription);
}
