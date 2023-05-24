package com.app.happybox.entity.subscript;

import com.app.happybox.audity.Period;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_FOOD_CALENDAR")
@Getter @ToString(exclude = "foodList") @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodCalendar extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    /* === 음식 달력일정 기본정보 === */
    @NotNull
    private String foodCalendarTitle;

    @Column(columnDefinition = "CLOB")
    private String foodCalendarDescription;

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    /* ========================== */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Subscription subscription;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "foodCalendar", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Food> foodList = new ArrayList<>();

    @Builder
    public FoodCalendar(String foodCalendarTitle, String foodCalendarDescription, LocalDate startDate, LocalDate endDate) {
        this.foodCalendarTitle = foodCalendarTitle;
        this.foodCalendarDescription = foodCalendarDescription;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }
}
