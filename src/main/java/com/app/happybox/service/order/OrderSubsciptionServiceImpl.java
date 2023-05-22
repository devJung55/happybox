package com.app.happybox.service.order;

import com.app.happybox.domain.OrderSubscriptionDTO;
import com.app.happybox.entity.order.OrderSubscription;
import com.app.happybox.repository.order.OrderSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("orderSubscription")
public class OrderSubsciptionServiceImpl implements OrderSubsciptionService {
    private final OrderSubscriptionRepository orderSubscriptionRepository;

    @Override
    public Page<OrderSubscriptionDTO> getListByWelfareId(Pageable pageable, Long welfareId, String subscriberName) {
        Page<OrderSubscription> orderSubscriptions = orderSubscriptionRepository.findSubscriberListByWelfareIdDescWithPaging_QueryDSL(pageable, welfareId, subscriberName);
        List<OrderSubscriptionDTO> orderSubscriptionDTOList = orderSubscriptions.get().map(this::adminToOrderSubscriptionDTO).collect(Collectors.toList());

        return new PageImpl<>(orderSubscriptionDTOList, pageable, orderSubscriptions.getTotalElements());
    }

    @Override
    public Long getMySubscriptionCountByMemberId(Long id) {
        return orderSubscriptionRepository.findSubscriptionCountByMemberId_QueryDSL(id);
    }

    @Override
    public OrderSubscriptionDTO getSubscriptionDetailByMemberId(Long memberId) {
        Optional<OrderSubscription> subscriptionOptional = orderSubscriptionRepository.findSubscriptionByMemberId_QueryDSL(memberId);
        if (subscriptionOptional.isPresent()) {
            return mypageToOrderSubscriptionDTO(subscriptionOptional.get());
        } else {
            return null;
        }
    }

    @Override @Transactional
    public void cancelSubscribeById(Long id) {
        orderSubscriptionRepository.findById(id).ifPresent(orderSubscription -> orderSubscriptionRepository.deleteById(id));
    }
}
