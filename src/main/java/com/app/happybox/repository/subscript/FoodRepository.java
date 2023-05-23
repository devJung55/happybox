package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> , FoodQueryDsl{

}
