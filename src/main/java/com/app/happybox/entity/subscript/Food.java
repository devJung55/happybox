package com.app.happybox.entity.subscript;

import com.app.happybox.entity.file.FoodFile;
import com.app.happybox.entity.user.Welfare;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_FOOD")
@Getter @ToString(exclude = {"foodCalendar", "foodFile"}) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "food")
    private FoodFile foodFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private FoodCalendar foodCalendar;
}
