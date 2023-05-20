package com.app.happybox.service.subscript;

import com.app.happybox.domain.SubscriptionDTO;
import com.app.happybox.domain.SubscriptionSearchDTO;
import com.app.happybox.entity.subscript.*;
import com.app.happybox.exception.SubscriptionNotFoundException;
import com.app.happybox.repository.subscript.FoodCalendarRepository;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("subscription")
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final FoodCalendarRepository foodCalendarRepository;

    // 최신 8개 조회
    @Override
    public List<SubscriptionDTO> findRecentTop8() {
        List<Subscription> subscriptions = subscriptionRepository.findTop8OrderByDate_QueryDSL();
        return collectFoodList(subscriptions);
    }

    //    주문 많은순 N개 조회
    @Override
    public List<SubscriptionDTO> findByOrderCount(Long limit) {
        List<Subscription> subscriptions = subscriptionRepository.findTopNByOrderCountOrderByOrderCount_QueryDSL(limit);
        return collectFoodList(subscriptions);
    }

    // 리뷰 많은순 N개 조회
    @Override
    public List<SubscriptionDTO> findByReviews(Long limit) {
        List<Subscription> subscriptions = subscriptionRepository.findTopNOrderByReviewCount_QueryDSL(limit);
        return collectFoodList(subscriptions);
    }

    @Override
    public List<SubscriptionDTO> findTop3ByLikeCount() {
        List<Subscription> subscriptions = subscriptionRepository.findTop3OrderByLikeCount();
        return collectFoodList(subscriptions);
    }

    // 검색 조회
    @Override
    public Page<SubscriptionDTO> findBySearch(Pageable pageable, SubscriptionSearchDTO searchDTO) {
        Page<Subscription> subscriptions = subscriptionRepository.findAllBySearchWithPaging_QueryDSL(pageable, searchDTO);
        return new PageImpl<>(
                collectFoodList(subscriptions.getContent()),
                pageable,
                subscriptions.getTotalElements()
        );
    }

    // 상세 조회
    @Override
    public SubscriptionDTO findByIdWithDetail(Long id) {
        Subscription subscription = subscriptionRepository
                .findByIdWithDetail_QueryDSL(id)
                .orElseThrow(() -> new SubscriptionNotFoundException());

        return subscriptionToDTO(subscription);
    }

    @Override
    public List<SubscriptionDTO> findAllBetweenDate(LocalDateTime dateTime) {
        LocalDateTime startDate = dateTime.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endDate = dateTime.with(TemporalAdjusters.lastDayOfMonth());

        List<Subscription> monthlySubs = subscriptionRepository.findTop3BetweenDateOrderByDateDesc_QueryDSL(startDate, endDate);

        return collectFoodList(monthlySubs);
    }

//    구독했는지 확인하는 용도
    @Override
    public Boolean existsByWelfareId(Long welfareId) {
        return subscriptionRepository.existsByWelfareId(welfareId);
    }

    private List<SubscriptionDTO> collectFoodList(List<Subscription> subscriptions) {
        List<SubscriptionDTO> collect = subscriptions.stream().map(subscription -> {
            List<FoodCalendar> foodCalendars = getFoodCalendars(subscription.getId());

            if (foodCalendars.isEmpty()) return subscriptionToDTO(subscription);

            Food food = foodCalendars.get(0).getFoodList().get(0);

            return subscriptionToDTO(subscription, foodToDTO(food));
        }).collect(Collectors.toList());
        return collect;
    }

    private List<FoodCalendar> getFoodCalendars(Long id) {
        return foodCalendarRepository.findAllInSubscriptionId(id);
    }
}
