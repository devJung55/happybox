package com.app.happybox.service.subscript;

import com.app.happybox.domain.FoodCalendarDTO;
import com.app.happybox.entity.subscript.Food;
import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.entity.subscript.FoodDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public interface FoodCalendarService {

    public List<FoodCalendarDTO> getFoodCalendars(Long subId);

    default FoodCalendarDTO foodCalendarToDTO(FoodCalendar foodCalendar) {
        return FoodCalendarDTO.builder()
                .foodCalendarDescription(foodCalendar.getFoodCalendarDescription())
                .foodCalendarTitle(foodCalendar.getFoodCalendarTitle())
                .id(foodCalendar.getId())
                .startDate(foodCalendar.getStartDate())
                .endDate(foodCalendar.getEndDate())
                .foodList(foodCalendar.getFoodList().stream().map(this::foodToDTO).collect(Collectors.toList()))
                .build();
    }

    default FoodDTO foodToDTO(Food food) {
        return FoodDTO.builder()
                .id(food.getId())
                .fileOrgName(food.getFileOrgName())
                .filePath(food.getFilePath())
                .fileUuid(food.getFileUuid())
                .foodName(food.getFoodName())
                .build();
    }
}
