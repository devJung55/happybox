package com.app.happybox.entity.board;

import com.app.happybox.audity.Period;
import com.app.happybox.entity.user.Member;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_BOARD")
@DynamicInsert
@Getter @ToString(exclude = {"boardLikes"}) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends Period {
    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;

    /* 게시판 기본 정보 */
    @ColumnDefault(value = "제목 없음")
    private String boardTitle;
    @NotNull
    private String boardContent;

    // 기본 리뷰 게시판
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'REVIEW'")
    private BoardType boardType;
    /* ============= */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", orphanRemoval = true)
    private List<BoardLike> boardLikes = new ArrayList<>();
}
