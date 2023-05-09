package com.app.happybox.service.subscript;

import com.app.happybox.entity.subscript.Food;
import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.SubscriptionDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface SubscriptionService {

    public List<SubscriptionDTO> findTop8();

    default SubscriptionDTO subscriptionToDTO(Subscription subscription) {
        return null;
    }

    default void getSubscription(List<Subscription> subscriptions, List<FoodCalendar> foodCalendars) {
        List<Subscription> subscriptionList = subscriptions;
        List<Long> ids = subscriptionList.stream().map(Subscription::getId).collect(Collectors.toList());

        List<Food> foodList = new ArrayList<>();

        // when
        List<FoodCalendar> foodCalendarList = foodCalendars;

        // then
        subscriptionList.forEach(subscription -> {
            foodCalendarList.stream()
                    .filter(foodCalendar -> foodCalendar.getSubscription().equals(subscription))
                    .forEach(foodCalendar -> foodCalendar.getFoodList().forEach(foodList::add));
        });
    }
}
