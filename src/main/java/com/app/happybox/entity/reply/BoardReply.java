package com.app.happybox.entity.reply;

import com.app.happybox.entity.board.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@DiscriminatorValue("BOARD")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardReply extends Reply {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Board board;
}
