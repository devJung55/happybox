package com.app.happybox.entity.file;

import com.app.happybox.entity.board.Board;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "TBL_BOARD_FILE")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardFile extends Files {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Board board;
}
