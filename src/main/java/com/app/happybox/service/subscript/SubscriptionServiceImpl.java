package com.app.happybox.service.subscript;

import com.app.happybox.domain.SubscriptionDTO;
import com.app.happybox.domain.SubscriptionSearchDTO;
import com.app.happybox.domain.user.SubscriptionWelFareDTO;
import com.app.happybox.entity.subscript.Food;
import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.entity.subscript.Subscription;
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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
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
    public Integer existsByMemberIdAndSubscriptionId(Long memberId, Long subscriptionId) {
        Integer integer = subscriptionRepository.existsByMemberIdAndSubscriptionId(memberId, subscriptionId);
        log.info("================================= {}", integer);
        return integer;
    }

    //    subscription id로 조회
    @Override
    public Boolean existsById(Long id) {
        return subscriptionRepository.existsById(id);
    }

    @Override
    @Transactional
    public void updateByDTO(SubscriptionWelFareDTO subscriptionWelFareDTO) {
        subscriptionRepository.findById(subscriptionWelFareDTO.getId()).ifPresent(subscription -> {
            subscription.setSubscriptionTitle(subscriptionWelFareDTO.getSubscriptionTitle());
            subscription.setSubscriptionContent(subscriptionWelFareDTO.getSubscriptionContent());
            subscription.setSubscriptionPrice(subscriptionWelFareDTO.getSubscriptionPrice());
        });
    }

    //    subscription정보 가져오기
    @Override
    public SubscriptionWelFareDTO getSubscriptionWelfareDTO(Long id) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        log.info(optionalSubscription.toString());
        if (optionalSubscription.isPresent()) {
            Subscription subscription = optionalSubscription.get();
            return subscriptionToWelfareDTO(subscription);
        } else {
            // 원하는 처리를 수행하거나 예외를 던지는 등의 방법으로 처리합니다.
            return null; // 또는 원하는 기본값을 반환합니다.
        }
    }

    @Override
    public Subscription getId(Long id) {
        return subscriptionRepository.findByWelfareId(id).get();
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
