package com.app.happybox.service.subscript;

import com.app.happybox.domain.SubscriptionLikeDTO;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.SubscriptionLike;
import com.app.happybox.entity.user.Member;
import com.app.happybox.exception.SubscriptionNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.subscript.SubscriptionLikeRepository;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        Member member = memberRepository.findById(memberId).orElseThrow(UserNotFoundException::new);
        boolean check = subscriptionLikeRepository.checkMemberLikesSubscription_QueryDSL(memberId, subscriptionId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(SubscriptionNotFoundException::new);

        if(check) {
            // 삭제
            subscriptionLikeRepository.deleteUserLikeByUserAndSubscription(memberId, subscriptionId);

            // 구독 좋아요 수 감소
            Integer replyLikeCount = subscription.getSubscriptLikeCount();
            subscription.setSubscriptLikeCount(--replyLikeCount);

        } else {
            // 저장
            subscriptionLikeRepository.save(new SubscriptionLike(member, subscription));

            // 구독 좋아요 수 증가
            Integer subscriptLikeCount = subscription.getSubscriptLikeCount() + 1;
            subscription.setSubscriptLikeCount(subscriptLikeCount);
        }

        return check;
    }

    @Override
    public boolean checkLike(Long subscriptionId, Long memberId) {
        return subscriptionLikeRepository.checkMemberLikesSubscription_QueryDSL(memberId, subscriptionId);
    }

    @Override
    public Page<SubscriptionLikeDTO> getListSubscriptionBookmarkByMemberId(Pageable pageable, Long memberId) {
        Page<SubscriptionLike> subscriptionLikes = subscriptionLikeRepository.findSubscriptionBookmarkByMemberIdWithPaging_QueryDSL(pageable, memberId);
        List<SubscriptionLikeDTO> subscriptionLikeDTOS = subscriptionLikes.get().map(this::subscriptionLikeToDTO).collect(Collectors.toList());
        return new PageImpl<>(subscriptionLikeDTOS, pageable, subscriptionLikes.getTotalElements());
    }

    @Override
    public void cancelSubscriptionById(Long id) {
        subscriptionLikeRepository.deleteById(id);
    }
}