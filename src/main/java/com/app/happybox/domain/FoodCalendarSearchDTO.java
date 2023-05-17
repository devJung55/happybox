package com.app.happybox.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter @ToString
public class FoodCalendarSearchDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate today;
    private Long subId;
}
