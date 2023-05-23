package com.app.happybox.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter @ToString
public class FoodCalendarDTO {
    private Long id;

    /* === 음식 달력일정 기본정보 === */
    @NotNull
    private String foodCalendarTitle;

    private String foodCalendarDescription;

    private LocalDate startDate;

    private LocalDate endDate;
    /* ============================ */
    private Long welfareId;

    private List<FoodDTO> foodList;

    @Builder
    public FoodCalendarDTO(Long id, String foodCalendarTitle, String foodCalendarDescription, LocalDate startDate, LocalDate endDate,Long welfareId ,List<FoodDTO> foodList) {
        this.id = id;
        this.foodCalendarTitle = foodCalendarTitle;
        this.foodCalendarDescription = foodCalendarDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.welfareId = welfareId;
        this.foodList = foodList;
    }

    public void setWelfareId(Long welfareId) {
        this.welfareId = welfareId;
    }
}
