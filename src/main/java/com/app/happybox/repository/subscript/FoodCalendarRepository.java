package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.FoodCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCalendarRepository extends JpaRepository<FoodCalendar, Long>, FoodCalendarQueryDsl {
}
