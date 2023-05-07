package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.SubscriptionLike;
import com.app.happybox.entity.user.Member;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class SubscriptionLikeRepositoryTests {
    @Autowired
    private SubscriptionLikeRepository subscriptionLikeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void checkMemberLikesSubscription_QueryDSL() {
        // given
        Optional<Member> member = memberRepository.findById(1L);
        Optional<Subscription> subscription = subscriptionRepository.findById(3L);

        // when
        if(!member.isPresent() || !subscription.isPresent()) fail("member 혹은 subscription 이 존재하지 않음.");

        checkMemberAlreadyLikes(member.get(), subscription.get());

        // then
    }

    @Test
    public void saveTest() {
        // given
        Optional<Member> member = memberRepository.findById(1L);
        Optional<Subscription> subscription = subscriptionRepository.findById(3L);

        // when
        if(!member.isPresent() || !subscription.isPresent()) fail("member 혹은 subscription 이 존재하지 않음.");

        // then
        if(!checkMemberAlreadyLikes(member.get(), subscription.get())) {
            Integer likeCount = subscription.get().getSubscriptLikeCount();

            SubscriptionLike subscriptionLike = new SubscriptionLike(member.get(), subscription.get());
            subscriptionLikeRepository.save(subscriptionLike);

            subscription.get().setSubscriptLikeCount(++likeCount);
        }
    }

    /* 좋아요 이미 눌렀는지 검사 */
    private boolean checkMemberAlreadyLikes(Member member, Subscription subscription) {

        return subscriptionLikeRepository.checkMemberLikesSubscription_QueryDSL(member, subscription);
        /* subscriptionLikes list 에서 확인하는 방법은 select 쿼리문이 너무 많이 발생 */
//            member.get().getSubscriptionLikes().forEach(subscriptionLike -> {
//                if(subscriptionLike.getSubscription().equals(subscription.get())) {
//                    log.info("좋아요 눌렀는지 여부 : " + true);
//                }
//            });
    }
}