package com.app.happybox.repository.subscript;

import com.app.happybox.domain.SubscriptionSearchDTO;
import com.app.happybox.entity.subscript.Food;
import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.exception.SubscriptionNotFoundException;
import com.app.happybox.repository.user.WelfareRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class SubscriptionRepositoryTests {
    @Autowired
    private WelfareRepository welfareRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private FoodCalendarRepository foodCalendarRepository;

    @Test
    public void saveTest() {
//        // given
//        Subscription subscription = new Subscription(
//                "강남 맛집",
//                28_000);
//        welfareRepository.findById(2L).ifPresent(welfare -> {
//            subscription.setWelfare(welfare);
//            subscriptionRepository.save(subscription);
//        });
//
//        // when
//
//        // then
    }

    @Test
    public void findByIdTest() {
        // given
        subscriptionRepository.findById(3L).map(Subscription::toString).ifPresent(log::info);

        // when

        // then
    }

    @Test
    public void findTop3BetweenDateOrderByDateDesc_QueryDSL_Test() {
        // given

        /* 이번달 시작, 다음달 시작 */
        LocalDateTime currentMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime nextMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay().plusDays(1L);

        List<Subscription> SubscriptionList =
                subscriptionRepository
                        .findTop3BetweenDateOrderByDateDesc_QueryDSL(
                                currentMonth,
                                nextMonth
                        );

        // when
        SubscriptionList.stream().map(Subscription::toString).forEach(log::info);

        // then
    }

    @Test
    public void findTopNByOrderCountOrderByOrderCount_QueryDSL(){
        // given
        Long limit = 8L;
        List<Subscription> subscriptionList = subscriptionRepository.findTopNByOrderCountOrderByOrderCount_QueryDSL(limit);

        // when

        // then
        subscriptionList.stream().map(Subscription::toString).forEach(log::info);
    }

    @Test
    public void findTopNOrderByReviewCount_QueryDSL_Test() {
        Long limit = 8L;

        subscriptionRepository
                .findTopNOrderByReviewCount_QueryDSL(limit)
                .stream()
                .map(Subscription::toString)
                .forEach(log::info);
    }

    @Test
    public void findAllBySearchWithPaging_QueryDSL_Test() {
        // given
        Page<Subscription> searchResult = subscriptionRepository
                .findAllBySearchWithPaging_QueryDSL(PageRequest.of(0, 10), new SubscriptionSearchDTO());

        // when

        // then
        searchResult.get().map(Subscription::toString).forEach(log::info);
    }

//    @Test
//    public void findTop8OrderByDate_QueryDSL() {
//        // given
//        List<Subscription> subscriptionList = subscriptionRepository.findTop8OrderByDate_QueryDSL();
//        List<Long> ids = subscriptionList.stream().map(Subscription::getId).collect(Collectors.toList());
//
//        List<Food> foodList = new ArrayList<>();
//
//        // when
//        List<FoodCalendar> foodCalendarList = foodCalendarRepository.findAllInSubscriptionIds(ids);
//
//        log.info(foodCalendarList.toString());
//        // then
//        subscriptionList.forEach(subscription -> {
//            foodCalendarList.stream()
//                    .filter(foodCalendar -> foodCalendar.getSubscription().equals(subscription))
//                    .forEach(foodCalendar -> foodCalendar.getFoodList().forEach(foodList::add));
//        });
//
//        log.info(subscriptionList.toString());
//        log.info(foodList.toString());
//    }

    @Test
    public void findByIdWithDetail_QueryDSL(){
        // given
        Subscription subscription = subscriptionRepository.findByIdWithDetail_QueryDSL(3L).orElseThrow(() -> new SubscriptionNotFoundException());

        // when

        // then
        log.info(subscription.toString());
    }
}