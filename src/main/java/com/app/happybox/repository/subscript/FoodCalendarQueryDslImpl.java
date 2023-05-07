package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.entity.subscript.QFoodCalendar;
import com.app.happybox.entity.subscript.Subscription;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static com.app.happybox.entity.subscript.QFoodCalendar.foodCalendar;

@RequiredArgsConstructor
public class FoodCalendarQueryDslImpl implements FoodCalendarQueryDsl {
    private final JPAQueryFactory query;


    @Override
    public List<FoodCalendar> findAllWithFoodListBySubscriptionAndDateBetween_QueryDSL(LocalDate date, Subscription subscription) {
        List<FoodCalendar> foodCalendarList = query.select(foodCalendar)
                .from(foodCalendar)
                .leftJoin(foodCalendar.foodList)
                .fetchJoin()
                .where(
                        foodCalendar.startDate.between(
                                date.with(TemporalAdjusters.firstDayOfMonth()),
                                date.with(TemporalAdjusters.lastDayOfMonth())
                        )
                                .and(foodCalendar.subscription.eq(subscription))
                )
                .orderBy(foodCalendar.startDate.asc())
                .fetch();
        return foodCalendarList;
    }
}
