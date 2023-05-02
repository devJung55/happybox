package com.app.happybox.entity.board;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_BOARD_LIKE")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLike extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
}
