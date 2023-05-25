package com.app.happybox.repository.subscript;

import com.app.happybox.domain.FoodCalendarSearchDTO;
import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.entity.subscript.Subscription;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static com.app.happybox.entity.subscript.QFoodCalendar.foodCalendar;


@RequiredArgsConstructor
public class FoodCalendarQueryDslImpl implements FoodCalendarQueryDsl {
    private final JPAQueryFactory query;


    @Override
    public List<FoodCalendar> findAllWithFoodListBySubscription_QueryDSL(FoodCalendarSearchDTO searchDTO) {
        // between 안의 from, to 계산, getToday 기준 저번달 ~ 다음달 범위 탐색
        LocalDate from = searchDTO.getToday().with(TemporalAdjusters.firstDayOfMonth()).minusMonths(1L);
        LocalDate to = searchDTO.getToday().with(TemporalAdjusters.lastDayOfMonth()).plusMonths(1L);

        BooleanExpression dateBetween = foodCalendar.startDate.between(from, to);
        BooleanExpression idEquals = foodCalendar.subscription.id.eq(searchDTO.getSubId());

        List<FoodCalendar> foodCalendarList = query.select(foodCalendar)
                .from(foodCalendar)
                .distinct()
                .leftJoin(foodCalendar.foodList).fetchJoin()
                .where(idEquals, dateBetween)
                .orderBy(foodCalendar.startDate.asc())
                .fetch();

        return foodCalendarList;
    }

    @Override
    public List<FoodCalendar> findAllInSubscriptionId(Long id) {
        LocalDateTime yearStart = LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
        LocalDateTime yearEnd = LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).plusDays(1L).atStartOfDay();
        List<FoodCalendar> foodCalendarList = query.select(foodCalendar)
                .distinct()
                .from(foodCalendar)
                .leftJoin(foodCalendar.foodList)
                .fetchJoin()
                .where(foodCalendar.subscription.id.eq(id),
                        foodCalendar.createdDate.goe(yearStart)
                                .and(foodCalendar.createdDate.loe(yearEnd))
                )
                .fetch();

        return foodCalendarList;
    }
}
