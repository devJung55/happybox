package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.repository.user.WelfareRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class FoodCalendarRepositoryTests {
    @Autowired
    private FoodCalendarRepository foodCalendarRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void saveTest() {
        // given
        subscriptionRepository.findById(3L).ifPresent(subscription -> {
            FoodCalendar foodCalendar = new FoodCalendar(
                    "빅맥주",
                    "일주일동안 빅맥만 먹으세요.",
                    LocalDate.now().plusWeeks(1L),
                    LocalDate.now().plusWeeks(2L)
            );
            foodCalendar.setSubscription(subscription);
            foodCalendarRepository.save(foodCalendar);
        });
        // when

        // then
    }

    @Test
    public void findAllWithFoodListBySubscriptionAndDateBetween_QueryDSL() {
        // given
        subscriptionRepository.findById(3L).ifPresent(subscription ->
                foodCalendarRepository
                        .findAllWithFoodListBySubscriptionAndDateBetween_QueryDSL(LocalDate.now(), subscription)
                        .stream().map(FoodCalendar::toString).forEach(log::info)
        );

        // when

        // then
        log.info(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString());
        log.info(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).toString());
    }
}