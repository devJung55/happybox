package com.app.happybox.service.subscript;

import com.app.happybox.domain.FoodCalendarDTO;
import com.app.happybox.domain.FoodCalendarSearchDTO;
import com.app.happybox.domain.FoodDTO;
import com.app.happybox.entity.subscript.Food;
import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.exception.FoodCalendarNotFoundException;
import com.app.happybox.exception.SubscriptionNotFoundException;
import com.app.happybox.repository.subscript.FoodCalendarRepository;
import com.app.happybox.repository.subscript.FoodRepository;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("foodCalendar")
public class FoodCalendarServiceImpl implements FoodCalendarService {
    private final FoodCalendarRepository foodCalendarRepository;
    private final FoodRepository foodRepository;
    private final SubscriptionRepository subscriptionRepository;


//    foodcalendar 등록
    @Override
    @Transactional
    public void saveFoodCalendar(FoodCalendarDTO foodCalendarDTO) {
        Long welfareId = foodCalendarDTO.getWelfareId();
        Optional<Subscription> subscription = subscriptionRepository.findByWelfareId(welfareId);
        FoodCalendar foodCalendar = toFoodCalendarEntity(foodCalendarDTO);
        foodCalendar.setSubscription(subscription.orElseThrow(()->new SubscriptionNotFoundException()));
        foodCalendarRepository.save(foodCalendar);
        log.info("=====================================================================");
        log.info(foodCalendar.toString());
        Long foodCalendarId = foodCalendar.getId();
        FoodCalendar foodCalendar1 = foodCalendarRepository.findById(foodCalendarId).orElseThrow(() -> new FoodCalendarNotFoundException());
        List<FoodDTO> foodDTOS = foodCalendarDTO.getFoodList();
        List<Food> foods = new ArrayList<>();
        foodDTOS.forEach(foodDTO -> {
            foods.add(toFoodEntity(foodDTO));
        });
        foods.forEach(food -> food.setFoodCalendar(foodCalendar1));
        log.info("=====================================================================");
        log.info(foods.toString());
        foodCalendar.setFoodList(foods);
    }

    @Override
    public List<FoodCalendarDTO> getFoodCalendars(FoodCalendarSearchDTO searchDTO) {
        if(searchDTO.getSubId() == null) throw new IllegalArgumentException("searchDTO subId not exists");
        if(searchDTO.getToday() == null) searchDTO.setToday(LocalDate.now());

        List<FoodCalendar> foodCalendars = foodCalendarRepository.findAllWithFoodListBySubscription_QueryDSL(searchDTO);
        return foodCalendars.stream().map(this::foodCalendarToDTO).collect(Collectors.toList());
    }
}
