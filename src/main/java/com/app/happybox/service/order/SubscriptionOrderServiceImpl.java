package com.app.happybox.service.order;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.domain.OrderInfoDTO;
import com.app.happybox.entity.order.OrderSubscription;
import com.app.happybox.entity.payment.Payment;
import com.app.happybox.entity.product.SubscriptionCart;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import com.app.happybox.exception.CartNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.exception.UserRoleUnsuitableException;
import com.app.happybox.repository.order.OrderSubscriptionRepository;
import com.app.happybox.repository.payment.PaymentRepository;
import com.app.happybox.repository.product.SubscriptionCartRepository;
import com.app.happybox.repository.user.MemberRepository;
import com.app.happybox.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("subscription")
@RequiredArgsConstructor
public class SubscriptionOrderServiceImpl implements SubscriptionOrderService {
    private final MemberRepository memberRepository;
    private final SubscriptionCartRepository subscriptionCartRepository;
    private final OrderSubscriptionRepository orderSubscriptionRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    // 장바구니 id들과 회원 id 받아옴
    public Integer saveSubscriptionOrder(OrderInfoDTO orderInfoDTO) {
        if (orderInfoDTO.getCartIds().isEmpty()) return -1;

        // 회원 ID로 회원 find
        Member member = memberRepository.findById(orderInfoDTO.getId()).orElseThrow(() -> {
            throw new UserNotFoundException(); // 회원 조회 실패 시 예외 발생
        });

        // 결제내역
        boolean isMember = member.getUserRole().equals(Role.MEMBER);

        // 알맞은 회원 타입이 아닐 경우 예외 발생
        if (!isMember) throw new UserRoleUnsuitableException();

        List<SubscriptionCart> subscriptionCarts = subscriptionCartRepository.findAllByIdsWithDetail_QueryDSL(orderInfoDTO.getCartIds());

        if (subscriptionCarts.isEmpty()) throw new CartNotFoundException();

        List<OrderSubscription> orderSubscriptions = subscriptionCarts.stream()
                .map(subscriptionCart ->
                        new OrderSubscription(
                                orderInfoDTO.getDeliveryName(),
                                orderInfoDTO.getDeliveryPhoneNumber(),
                                new Address(orderInfoDTO.getAddressDTO().getZipcode(), orderInfoDTO.getAddressDTO().getFirstAddress(), orderInfoDTO.getAddressDTO().getAddressDetail()),
                                subscriptionCart.getSubscription(),
                                subscriptionCart.getMember(),
                                subscriptionCart.getSubOption()
                        )
                ).collect(Collectors.toList());

        orderSubscriptions.forEach(order -> {
            // 주문 저장
            orderSubscriptionRepository.save(order);
            // 결제내역 저장
            paymentRepository.save(new Payment(order.getSubscription().getSubscriptionPrice().longValue(), order.getMember(), order));
            // 구독 entity 업데이트
            Long orderCount = order.getSubscription().getOrderCount();
            order.getSubscription().setOrderCount(++orderCount);
        });

        // 장바구니 삭제
        subscriptionCartRepository.deleteAllById(orderInfoDTO.getCartIds());

        return orderSubscriptions.size();
    }
}
