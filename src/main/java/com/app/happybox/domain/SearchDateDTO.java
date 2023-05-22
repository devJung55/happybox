package com.app.happybox.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
public class SearchDateDTO {
    private String year;
    private String month;
    private String day;
    private LocalDateTime setDate;

    public void setSetDate(LocalDateTime setDate) {
        this.setDate = setDate;
    }
}
