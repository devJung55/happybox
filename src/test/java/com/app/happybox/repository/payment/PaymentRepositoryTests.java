package com.app.happybox.repository.payment;

import com.app.happybox.entity.order.OrderSubscription;
import com.app.happybox.entity.payment.Payment;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.app.happybox.exception.SubscriptionNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.order.OrderSubscriptionRepository;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.MemberRepository;
import com.app.happybox.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class PaymentRepositoryTests {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderSubscriptionRepository orderSubscriptionRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void saveTest(){
        Member member = memberRepository.findById(1L).orElseThrow(() -> new UserNotFoundException());
        Subscription subscription = subscriptionRepository.findById(3L).orElseThrow(() -> new SubscriptionNotFoundException());
        OrderSubscription orderSubscription = new OrderSubscription("강민구", "01034442331",
                new Address("11111", "수원", "매탄동"),
                subscription,
                member,
                null
        );

        orderSubscriptionRepository.save(orderSubscription);

        Payment payment = new Payment(subscription.getSubscriptionPrice().longValue(), orderSubscription.getMember(), orderSubscription);
        paymentRepository.save(payment);
    }
}