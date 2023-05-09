package com.app.happybox.service.subscript;

import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.SubscriptionDTO;
import com.app.happybox.repository.subscript.FoodCalendarRepository;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("subscription")
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final FoodCalendarRepository foodCalendarRepository;

    // 최신 8개 조회
    @Override
    public List<SubscriptionDTO> findRecentTop8() {
        List<Subscription> subscriptions = subscriptionRepository.findTop8OrderByDate_QueryDSL();
        return collectFoodList(subscriptions, getFoodCalendars(subscriptions));
    }

    //    주문 많은순 N개 조회
    @Override
    public List<SubscriptionDTO> findByOrderCount(Long limit) {
        List<Subscription> subscriptions = subscriptionRepository.findTopNByOrderCountOrderByOrderCount_QueryDSL(limit);
        return collectFoodList(subscriptions, getFoodCalendars(subscriptions));
    }

    // 리뷰 많은순 N개 조회
    @Override
    public List<SubscriptionDTO> findByReviews(Long limit) {
        List<Subscription> subscriptions = subscriptionRepository.findTopNOrderByReviewCount_QueryDSL(limit);
        return collectFoodList(subscriptions, getFoodCalendars(subscriptions));
    }

    private List<FoodCalendar> getFoodCalendars(List<Subscription> subscriptions) {
        List<Long> ids = subscriptions.stream().map(Subscription::getId).collect(Collectors.toList());
        return foodCalendarRepository.findAllInSubscriptionIds(ids);
    }
}
