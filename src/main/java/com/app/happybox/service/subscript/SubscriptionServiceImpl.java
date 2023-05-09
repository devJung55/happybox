package com.app.happybox.service.subscript;

import com.app.happybox.entity.subscript.Food;
import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.SubscriptionDTO;
import com.app.happybox.repository.subscript.FoodCalendarRepository;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("subscription")
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final FoodCalendarRepository foodCalendarRepository;

    @Override
    public List<SubscriptionDTO> findTop8() {
        return null;
    }
}
