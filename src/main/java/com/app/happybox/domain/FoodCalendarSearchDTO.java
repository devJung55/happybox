package com.app.happybox.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @ToString
public class FoodCalendarSearchDTO {
    @JsonFormat(shape = JsonFormat.Shape.OBJECT, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate today;
    private Long subId;
}
