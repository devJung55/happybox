package com.app.happybox.entity.board;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_RECIPE_BOARD_LIKE")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeBoardLike extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private RecipeBoard recipeBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public RecipeBoardLike(RecipeBoard recipeBoard, User user) {
        this.recipeBoard = recipeBoard;
        this.user = user;
    }
}
