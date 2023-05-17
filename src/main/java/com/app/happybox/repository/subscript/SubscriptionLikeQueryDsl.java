package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.SubscriptionLike;
import com.app.happybox.entity.user.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscriptionLikeQueryDsl {
//    좋아요 눌렀는지 여부 확인
    public boolean checkMemberLikesSubscription_QueryDSL(Member member, Subscription subscription);

//    회원의 해당 좋아요 취소(삭제)
    public void deleteUserLikeByUserAndSubscription(Member member, Subscription subscription);

//    마이페이지 구독 조회
    public Page<SubscriptionLike> findSubscriptionBookmarkByMemberIdWithPaging_QueryDSL(Pageable pageable, Long memberId);
}
