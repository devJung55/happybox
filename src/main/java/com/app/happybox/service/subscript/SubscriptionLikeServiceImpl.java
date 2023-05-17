package com.app.happybox.service.subscript;

import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReplyLike;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.SubscriptionLike;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.app.happybox.exception.ReplyNotFoundException;
import com.app.happybox.exception.SubscriptionNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.subscript.SubscriptionLikeRepository;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Qualifier("subscriptionLike")
@Slf4j
public class SubscriptionLikeServiceImpl implements SubscriptionLikeService {

    private final SubscriptionLikeRepository subscriptionLikeRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkOutLike(Long subscriptionId, Long memberId) {
        boolean check = subscriptionLikeRepository.checkMemberLikesSubscription_QueryDSL(memberId, subscriptionId);
        log.info(String.valueOf(check));
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(SubscriptionNotFoundException::new);

        if(check) {
            // 삭제
            subscriptionLikeRepository.deleteUserLikeByUserAndSubscription(memberId, subscriptionId);

            // 구독 좋아요 수 감소
            Integer replyLikeCount = subscription.getSubscriptLikeCount();
            subscription.setSubscriptLikeCount(--replyLikeCount);

        } else {
            Member member = memberRepository.findById(memberId).orElseThrow(UserNotFoundException::new);

            // 저장
            subscriptionLikeRepository.save(new SubscriptionLike(member, subscription));

            // 구독 좋아요 수 증가
            Integer subscriptLikeCount = subscription.getSubscriptLikeCount();
            subscription.setSubscriptLikeCount(subscriptLikeCount);
        }

        return check;
    }

    @Override
    public boolean checkLike(Long subscriptionId, Long memberId) {
        return subscriptionLikeRepository.checkMemberLikesSubscription_QueryDSL(memberId, subscriptionId);
    }
}
