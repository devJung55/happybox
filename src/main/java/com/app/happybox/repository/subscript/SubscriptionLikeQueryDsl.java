package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;

public interface SubscriptionLikeQueryDsl {
//    좋아요 눌렀는지 여부 확인
    public boolean checkMemberLikesSubscription_QueryDSL(Member member, Subscription subscription);

//    회원의 해당 좋아요 취소(삭제)
    public void deleteUserLikeByUserAndSubscription(Member member, Subscription subscription);
}
