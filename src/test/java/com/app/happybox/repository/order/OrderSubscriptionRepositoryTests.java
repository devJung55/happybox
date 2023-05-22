package com.app.happybox.repository.order;

import com.app.happybox.entity.order.OrderSubscription;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class OrderSubscriptionRepositoryTests {
    @Autowired
    private OrderSubscriptionRepository orderSubscriptionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void saveTest(){
        // given
        Optional<Member> member = memberRepository.findById(259L);
        Optional<Subscription> subscription = subscriptionRepository.findById(27L);
        Address address = new Address("11111", "서울시 역삼동", "코리아IT");

        // when
        if (!member.isPresent() || !subscription.isPresent()) fail("member 혹은 subscription 없음.");

//        OrderSubscription orderSubscription = new OrderSubscription(
//                "정지영",
//                "01012344321",
//                address,
//                subscription.get(),
//                member.get()
//        );

        orderSubscriptionRepository.save(null);

        // then
    }

    @Test
    public void findSubscriptionByMemberId_QueryDSL_Test() {
        orderSubscriptionRepository.findSubscriptionByMemberId_QueryDSL(1L)
                .ifPresent(orderSubscription -> {
                    log.info(orderSubscription.getMember().getMemberName());
                    log.info(orderSubscription.getSubscription().getWelfare().getWelfareName());
                });
    }

    @Test
    public void findSubscriptionCountByMemberId_QueryDSL_Test() {
        log.info("구독 수 : " + orderSubscriptionRepository.findSubscriptionCountByMemberId_QueryDSL(1L));
    }

    @Test
    public void findSubscriberListByWelfareIdDescWithPaging_QueryDSL_Test() {
        String name = "강민";

        orderSubscriptionRepository.findSubscriberListByWelfareIdDescWithPaging_QueryDSL(PageRequest.of(0, 1), 26L, name)
                .stream().map(orderSubscription -> orderSubscription.getMember().toString()).forEach(log::info);
    }

    @Test
    public void findSubscriberCountByWelfareId_QueryDSL_Test() {
        log.info("구독자 수 : " + orderSubscriptionRepository.findSubscriberCountByWelfareId_QueryDSL(26L));
    }
}