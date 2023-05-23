package com.app.happybox.service.subscript;

import com.app.happybox.domain.FoodCalendarDTO;
import com.app.happybox.domain.FoodCalendarSearchDTO;
import com.app.happybox.entity.subscript.Food;
import com.app.happybox.entity.subscript.FoodCalendar;
import com.app.happybox.domain.FoodDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface FoodCalendarService {

    public void saveFoodCalendar(FoodCalendarDTO foodCalendarDTO);


    public List<FoodCalendarDTO> getFoodCalendars(FoodCalendarSearchDTO searchDTO);

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

    default FoodCalendar toFoodCalendarEntity(FoodCalendarDTO foodCalendarDTO){
        return FoodCalendar.builder()
                .foodCalendarTitle(foodCalendarDTO.getFoodCalendarTitle())
                .foodCalendarDescription(foodCalendarDTO.getFoodCalendarDescription())
                .startDate(foodCalendarDTO.getStartDate())
                .endDate(foodCalendarDTO.getEndDate())
                .build();
    }

    default Food toFoodEntity(FoodDTO foodDTO){
        return Food.builder()
                .foodName(foodDTO.getFoodName())
                .fileOrgName(foodDTO.getFileOrgName())
                .filePath(foodDTO.getFilePath())
                .fileUuid(foodDTO.getFileUuid())
                .build();
    }

}
