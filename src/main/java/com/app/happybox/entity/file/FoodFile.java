package com.app.happybox.entity.file;

import com.app.happybox.entity.subscript.Food;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_FOOD_FILE")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodFile {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String filePath;
    @NotNull
    private String fileUuid;
    @NotNull
    private String fileOrgName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Food food;
}
