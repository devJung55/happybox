package com.app.happybox.entity.subscript;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_FOOD")
@Getter @ToString(exclude = {"foodCalendar"}) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String foodName;

    @NotNull
    private String filePath;
    @NotNull
    private String fileUuid;
    @NotNull
    private String fileOrgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private FoodCalendar foodCalendar;

    @Builder
    public Food(String foodName, String filePath, String fileUuid, String fileOrgName) {
        this.foodName = foodName;
        this.filePath = filePath;
        this.fileUuid = fileUuid;
        this.fileOrgName = fileOrgName;
    }

    public void setFoodCalendar(FoodCalendar foodCalendar) {
        this.foodCalendar = foodCalendar;
    }
}
