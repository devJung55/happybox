package com.app.happybox.entity.file;

import com.app.happybox.entity.subscript.Food;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_FOOD_FILE")
@DiscriminatorValue("FOOD")
@Getter @ToString(exclude = "food",callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodFile extends Files {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Food food;
}
