package com.app.happybox.entity.reply;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name = "TBL_RECIPE_BOARD_REPLY")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeBoardReply extends Reply {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private RecipeBoard recipeBoard;

    public RecipeBoardReply(String replyContent, User user, RecipeBoard recipeBoard) {
        super(replyContent, user);
        this.recipeBoard = recipeBoard;
    }
}
