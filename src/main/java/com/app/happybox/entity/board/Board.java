package com.app.happybox.entity.board;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.user.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 기본 게시판
 * */
@Entity @Table(name = "TBL_BOARD")
@DynamicInsert @Inheritance(strategy = InheritanceType.JOINED)
@Getter @ToString(exclude = {"boardFiles"}) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Board extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    /* 게시판 기본 정보 */
    @ColumnDefault(value = "'제목 없음'")
    private String boardTitle;
    @NotNull
    private String boardContent;

    /* ============= */

    /* 게시판 파일 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<BoardFile> boardFiles = new ArrayList<>();

    public Board(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    public Board(String boardContent) {
        this.boardContent = boardContent;
    }
}
