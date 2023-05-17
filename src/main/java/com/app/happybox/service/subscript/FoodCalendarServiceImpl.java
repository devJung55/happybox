package com.app.happybox.service.subscript;

import com.app.happybox.domain.FoodCalendarDTO;
import com.app.happybox.domain.FoodCalendarSearchDTO;
import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.repository.subscript.FoodCalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("foodCalendar")
public class FoodCalendarServiceImpl implements FoodCalendarService {
    private final FoodCalendarRepository foodCalendarRepository;


    @Override
    public List<FoodCalendarDTO> getFoodCalendars(FoodCalendarSearchDTO searchDTO) {
        if(searchDTO.getSubId() == null) throw new IllegalArgumentException("searchDTO subId not exists");
        if(searchDTO.getToday() == null) searchDTO.setToday(LocalDate.now());

        List<FoodCalendar> foodCalendars = foodCalendarRepository.findAllWithFoodListBySubscription_QueryDSL(searchDTO);
        return foodCalendars.stream().map(this::foodCalendarToDTO).collect(Collectors.toList());
    }
}
