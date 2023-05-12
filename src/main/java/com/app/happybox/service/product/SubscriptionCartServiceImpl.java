package com.app.happybox.service.product;

import com.app.happybox.entity.product.*;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.app.happybox.exception.ProductNotFoundException;
import com.app.happybox.exception.SubscriptionNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.product.SubscriptionCartRepository;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("subscriptionCartService")
public class SubscriptionCartServiceImpl implements SubscriptionCartService {
    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionCartRepository subscriptionCartRepository;

    @Override
    public List<SubscriptionCartDTO> findAllByUserId(Long id) {
        return subscriptionCartRepository.findAllByUserId_QueryDSL(id).stream()
                .map(this::cartToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Long saveCart(SubscriptionCartDTO cartDTO, Long memberId, Long subscriptionId) {
        Member member = null;
        Subscription subscription = null;
        try {
            member = memberRepository.findById(memberId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });
            subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> {
                throw new SubscriptionNotFoundException();
            });
        } catch (RuntimeException e) {
            log.error("구독 번호 혹은 유저 번호 잘못됨.");
            return -1L;
        }

        SubscriptionCart cart = subscriptionCartRepository.save(subscriptionCartDTOToEntity(cartDTO, member, subscription));

        return cart.getId();
    }
}
